package com.fasolutions.finance.application

import com.fasolutions.finance.application.model.RangeFilters
import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.application.service.ReportCalculator
import com.fasolutions.finance.application.service.ReportFilter
import com.fasolutions.finance.presenter.CsvReportPresenter
import java.io.File

fun main() {

    val rangeFilters = RangeFilters(
        observedPayout = RangeFilters.Range(0.0, 63.0),
        price = RangeFilters.Range(0.0, 100.0),
        fairPricePercent = RangeFilters.Range(-90.0, 0.0),
        fairAveragePricePercent = RangeFilters.Range(-90.0, -14.0),
        roic = RangeFilters.Range(10.0, 50.0),
        dividendLastFiveYears = RangeFilters.Range(5.0, 30.0),
        dividendLastTwoYears = RangeFilters.Range(4.5, 30.0),
        dividendNextTreeYears = RangeFilters.Range(7.0, 30.0),
    )

    val companyRepository = CompanyRepository()
    val calculator = ReportCalculator(companyRepository)
    val filter = ReportFilter(rangeFilters)
    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    //val companyCodes = listOf("BRSR6")
    val companyReports = calculator.calculate(companyCodes)
    val reportsFiltered = filter.calculateFiltered(companyReports)
    val presenter = CsvReportPresenter()
    val presentation = presenter.draw(reportsFiltered)
    println(presentation)

    val path = "/Users/falessio/financeReports"
    val report = File("$path/finance.csv")
    report.writeText(presentation)
}