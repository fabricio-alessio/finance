package com.fasolutions.finance.presenter

import com.fasolutions.finance.application.model.CompanyReport
import com.fasolutions.finance.application.service.ReportPresenter

class CsvReportPresenter : ReportPresenter {

    override fun draw(companyReports: List<CompanyReport>): String {
        val header = "code|name|sector|div5|div2|divF|oPayout|vpa|quantity|total|price|avgPrice|%avgPrice|fairPrice|%fairPrice|fairAvgPrice|%fairAvgPrice|lpa|%lpaPrice|val30Days|val5Days|roic|%total|filtered|"

        return buildString {
            append(header)
            append("\n")
            companyReports.forEach { companyReport ->
                append(drawLine(companyReport))
                append("\n")
            }
        }
    }

    private fun drawLine(companyReport: CompanyReport): String {

        return "${companyReport.code}|" +
                "${companyReport.name}|" +
                "${companyReport.sector}|" +
                "${companyReport.dividendLastFiveYears.format()}|" +
                "${companyReport.dividendLastTwoYears.format()}|" +
                "${companyReport.dividendNextTreeYears.format()}|" +
                "${companyReport.observedPayout.format()}|" +
                "${companyReport.vpa.format()}|" +
                "${companyReport.quantity.format()}|" +
                "${companyReport.totalPrice.format()}|" +
                "${companyReport.price.format()}|" +
                "${companyReport.averagePrice.format()}|" +
                "${companyReport.averagePricePercent.format()}|" +
                "${companyReport.fairPrice.format()}|" +
                "${companyReport.fairPricePercent.format()}|" +
                "${companyReport.fairAveragePrice.format()}|" +
                "${companyReport.fairAveragePricePercent.format()}|" +
                "${companyReport.lpa.format()}|" +
                "${companyReport.lpaPricePercent.format()}|" +
                "${companyReport.valorization30Days.format()}|" +
                "${companyReport.valorization5Days.format()}|" +
                "${companyReport.roic.format()}|" +
                "${companyReport.totalPercent.format()}|" +
                "${companyReport.filtered}|"
    }
}