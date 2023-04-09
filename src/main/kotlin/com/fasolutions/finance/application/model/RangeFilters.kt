package com.fasolutions.finance.application.model

data class RangeFilters(
    private val observedPayout: Range,
    private val price: Range,
    private val fairPricePercent: Range,
    private val fairAveragePricePercent: Range,
    private val roic: Range,
    private val dividendLastFiveYears: Range,
    private val dividendLastTwoYears: Range,
    private val dividendNextTreeYears: Range,
) {
    data class Range(
        private val minimum: Double,
        private val maximum: Double
    ) {
        fun filter(value: Double) = value in minimum..maximum
    }

    fun filterObservedPayout(value: Double) = observedPayout.filter(value)
    fun filterPrice(value: Double) = price.filter(value)
    fun filterFairPricePercent(value: Double) = fairPricePercent.filter(value)
    fun filterFairAveragePricePercent(value: Double) = fairAveragePricePercent.filter(value)
    fun filterRoic(value: Double) = roic.filter(value)
    fun filterDividendLastFiveYears(value: Double) = dividendLastFiveYears.filter(value)
    fun filterDividendLastTwoYears(value: Double) = dividendLastTwoYears.filter(value)
    fun filterDividendNextTreeYears(value: Double) = dividendNextTreeYears.filter(value)
}
