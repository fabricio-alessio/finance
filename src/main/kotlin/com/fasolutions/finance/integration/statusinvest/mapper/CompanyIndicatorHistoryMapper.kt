package com.fasolutions.finance.integration.statusinvest.mapper

import com.fasolutions.finance.application.model.CompanyIndicatorHistory
import com.fasolutions.finance.application.model.IndicatorCode
import com.fasolutions.finance.integration.statusinvest.model.CompanyIndicatorHistoryResponse

class CompanyIndicatorHistoryMapper {

    companion object {
        val indicatorCodeMap = mapOf(
            "dy" to IndicatorCode.DY,
            "lpa" to IndicatorCode.LPA,
            "vpa" to IndicatorCode.VPA,
            "dividaliquida_ebitda" to IndicatorCode.DIV_EBITDA,
            "margemebitda" to IndicatorCode.MARG_EBITDA,
            "roe" to IndicatorCode.ROE,
            "roic" to IndicatorCode.ROIC
        )
    }

    fun map(response: CompanyIndicatorHistoryResponse): CompanyIndicatorHistory {
        val companyCode = response.data.keys.first()
        val indicators = response.data.values.first()
        return CompanyIndicatorHistory(
            code = companyCode.uppercase(),
            indicators = indicators.filter(this::knownIndicator).map(this::mapIndicator)
        )
    }

    private fun knownIndicator(indicator: CompanyIndicatorHistoryResponse.Indicator): Boolean {
        return indicatorCodeMap.containsKey(indicator.key)
    }

    private fun mapIndicator(indicator: CompanyIndicatorHistoryResponse.Indicator): CompanyIndicatorHistory.Indicator {
        return CompanyIndicatorHistory.Indicator(
            code = indicatorCodeMap[indicator.key]!!,
            actual = indicator.actual,
            average = indicator.avg,
            ranks = indicator.ranks.map(this::mapRank)
        )
    }

    private fun mapRank(rank: CompanyIndicatorHistoryResponse.Rank) =
        CompanyIndicatorHistory.Rank(
            rank = rank.rank,
            value = rank.value
        )
}
