package com.fasolutions.finance.application.model

import kotlin.math.sqrt

data class CompanyIndicatorHistory(
    val code: String,
    val indicators: List<Indicator>

) {
    companion object {
        const val GARAM_INDEX = 22.5
    }

    data class Rank(
        val rank: Int,
        val value: Double
    )

    data class Indicator(
        val code: IndicatorCode,
        val actual: Double,
        val average: Double,
        val ranks: List<Rank>
    )

    fun valueByCode(code: IndicatorCode): Double {
        return indicators.first { it.code == code }.actual
    }

    fun fairPrice(): Double {
        val lpa = valueByCode(IndicatorCode.LPA)
        val vpa = valueByCode(IndicatorCode.VPA)
        val multi = GARAM_INDEX * lpa * vpa
        return if (multi <= 0) {
            0.0
        } else {
            sqrt(GARAM_INDEX * lpa * vpa)
        }
    }

    fun averageByCode(code: IndicatorCode): Double {
        return indicators.first { it.code == code }.average
    }

    fun fairAveragePrice(): Double {
        val lpa = averageByCode(IndicatorCode.LPA)
        val vpa = averageByCode(IndicatorCode.VPA)
        val multi = GARAM_INDEX * lpa * vpa
        return if (multi <= 0) {
            0.0
        } else {
            sqrt(GARAM_INDEX * lpa * vpa)
        }
    }
}
