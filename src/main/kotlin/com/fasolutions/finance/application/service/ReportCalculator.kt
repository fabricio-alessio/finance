package com.fasolutions.finance.application.service

import com.fasolutions.finance.application.model.CompanyReport
import com.fasolutions.finance.application.model.IndicatorCode
import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.application.repository

class ReportCalculator(
    val companyRepository: CompanyRepository
) {
    fun calculate(codes: List<String>): List<CompanyReport> {
        val reports = codes.mapNotNull(this::calculateForCode)
        val sumTotals = reports.sumOf { it.totalPrice ?: 0.0 }
        return reports.map {
            it.copy(
                totalPercent = (it.totalPrice ?: 0.0) / sumTotals * 100
            )
        }
    }

    private fun calculateForCode(code: String): CompanyReport? {
        val basics = repository.loadBasics(code)
        val provents = repository.loadProvents(code)
        val price = repository.loadPrice(code).price
        val indicatorHistory = repository.loadIndicatorHistory(code)
        val position = repository.loadPositions(code).positions.lastOrNull()
        val prices = repository.loadPrices(code)
        val price30DaysAgo = prices.nearAtCountDaysAgo(30)
        val price5DaysAgo = prices.nearAtCountDaysAgo(5)
        val evaluations = repository.loadEvaluations(code)
        if (price > 0) {
            return CompanyReport(
                code = code,
                name = basics.name,
                sector = basics.sector,
                subSector = basics.subSector,
                type = basics.type,
                dividendLastFiveYears = provents.averageLastYears(5) / price * 100,
                dividendLastTwoYears = provents.averageLastYears(2) / price * 100,
                price = price,
                fairPrice = indicatorHistory.fairPrice(),
                fairPricePercent = (price / indicatorHistory.fairPrice() * 100) - 100,
                lpa = indicatorHistory.valueByCode(IndicatorCode.LPA),
                vpa = indicatorHistory.valueByCode(IndicatorCode.VPA),
                lpaPricePercent = indicatorHistory.valueByCode(IndicatorCode.LPA) / price * 100,
                quantity = position?.totalQuantity,
                averagePrice = position?.averagePrice,
                averagePricePercent = position?.averagePrice?.let { (price / it * 100) - 100 },
                totalPrice = position?.totalQuantity?.let { it * price },
                roic = indicatorHistory.valueByCode(IndicatorCode.ROIC),
                totalPercent = 0.0,
                fairAveragePrice = indicatorHistory.fairAveragePrice(),
                fairAveragePricePercent = (price / indicatorHistory.fairAveragePrice() * 100) - 100,
                valorization30Days = (price - price30DaysAgo) / price30DaysAgo * 100,
                valorization5Days = (price - price5DaysAgo) / price5DaysAgo * 100,
                observedPayout = evaluations.observedPayout,
                dividendNextTreeYears = evaluations.averageNextProvents() / price * 100,
                filtered = false
            )
        } else {
            return null
        }
    }
}