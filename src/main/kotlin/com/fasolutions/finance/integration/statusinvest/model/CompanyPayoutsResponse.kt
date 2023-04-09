package com.fasolutions.finance.integration.statusinvest.model

data class CompanyPayoutsResponse(
    var code: String?,
    val actual: Double,
    val avg: Double,
    val minValue: Double,
    val minValueRank: Integer,
    val maxValue: Double,
    val maxValueRank: Integer,
    val chart: Chart
) {
    data class Chart(
        val category: List<Integer>,
        val series: Series
    )

    data class Series(
        val percentual: List<Percentual>,
        val proventos: List<Proventos>
    )

    data class Percentual(
        val value: Double
    )

    data class Proventos(
        val value: Double
    )
}
