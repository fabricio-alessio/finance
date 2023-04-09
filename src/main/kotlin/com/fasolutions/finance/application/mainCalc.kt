package com.fasolutions.finance.application

import com.fasolutions.finance.application.model.IndicatorCode
import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.presenter.format
import java.io.File

val repository = CompanyRepository()

fun main() {
    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    val lines = mutableListOf<String>()
    val header = "code|div5|div2|vpa|quantity|total|price|avgPrice|%avgPrice|fairPrice|%fairPrice|lpa|%lpaPrice|"

    println(header)
    lines.add(header)
    companyCodes.forEach {code ->
        createLine(code)?.let {
            println(it)
            lines.add(it)
        }
    }

    val path = "/Users/falessio/financeReports"
    val report = File("$path/finance.csv")
    report.printWriter().use { writer ->
        lines.forEach {line ->
            writer.println(line)
        }
    }
}

fun createLine(code: String): String? {
    val provents = repository.loadProvents(code)
    val price = repository.loadPrice(code).price
    val indicatorHistory = repository.loadIndicatorHistory(code)
    val position = repository.loadPositions(code).positions.firstOrNull()
    if (price > 0) {
        val dividendLastFiveYears = provents.averageLastYears(5) / price * 100
        val dividendLastTwoYears = provents.averageLastYears(2) / price * 100
        val fairPricePercent = (price / indicatorHistory.fairPrice() * 100) - 100
        val priceStr = price.format()
        val div5years = dividendLastFiveYears.format()
        val div2years = dividendLastTwoYears.format()
        val fairPriceP = fairPricePercent.format()
        val lpa = indicatorHistory.valueByCode(IndicatorCode.LPA).format()
        val lpaPricePercent = indicatorHistory.valueByCode(IndicatorCode.LPA) / price * 100
        val lapPriceP = lpaPricePercent.format()
        val vpa = indicatorHistory.valueByCode(IndicatorCode.VPA).format()
        val fairPrice = indicatorHistory.fairPrice().format()
        val quantity = position?.totalQuantity?.toString() ?: ""
        val avgPrice = position?.averagePrice?.format() ?: ""
        val avgPricePercent = position?.averagePrice?.let { (price / it * 100) - 100 }
        val avgPriceP = avgPricePercent?.format() ?: ""
        val total = if (position != null) (position.totalQuantity * price).format() else ""

        return "$code|$div5years|$div2years|$vpa|$quantity|$total|$priceStr|$avgPrice|$avgPriceP|$fairPrice|$fairPriceP|$lpa|$lapPriceP|"
    } else {
        return null
    }
}

