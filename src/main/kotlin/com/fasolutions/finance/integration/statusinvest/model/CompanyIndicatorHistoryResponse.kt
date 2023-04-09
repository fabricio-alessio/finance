package com.fasolutions.finance.integration.statusinvest.model

data class CompanyIndicatorHistoryResponse(
    val success: Boolean,
    val data: Map<String, List<Indicator>>
) {
    data class Indicator(
        val key: String,
        val actual: Double,
        val avg: Double,
        val avgDifference: Double,
        val minValue: Double,
        val minValueRank: Int,
        val maxValue: Double,
        val maxValueRank: Int,
        val ranks: List<Rank>
    )

    data class Rank(
        val timeType: Int,
        val rank: Int,
        val value: Double,
        val rankN: Int
    )
}
