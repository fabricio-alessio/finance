package com.fasolutions.finance.application.service

import com.fasolutions.finance.application.model.CompanyReport
import com.fasolutions.finance.application.model.RangeFilters

class ReportFilter(
    private val rangeFilters: RangeFilters
) {
    fun calculateFiltered(reports: List<CompanyReport>) =
        reports.map {
            it.copy(
                filtered = calculate(it)
            )
        }

    private fun calculate(report: CompanyReport): Boolean {
        if (!rangeFilters.filterObservedPayout(report.observedPayout)) return false
        if (!rangeFilters.filterPrice(report.price)) return false
        if (!rangeFilters.filterRoic(report.roic) && report.roic != 0.0) return false
        if (!rangeFilters.filterFairPricePercent(report.fairPricePercent)) return false
        if (!rangeFilters.filterFairAveragePricePercent(report.fairAveragePricePercent)) return false
        if (!rangeFilters.filterDividendLastFiveYears(report.dividendLastFiveYears)) return false
        if (!rangeFilters.filterDividendLastTwoYears(report.dividendLastTwoYears)) return false
        if (!rangeFilters.filterDividendNextTreeYears(report.dividendNextTreeYears)) return false

        return true
    }
}
