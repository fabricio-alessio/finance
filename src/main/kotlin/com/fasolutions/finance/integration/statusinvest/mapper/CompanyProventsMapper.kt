package com.fasolutions.finance.integration.statusinvest.mapper

import com.fasolutions.finance.application.model.CompanyProvents
import com.fasolutions.finance.integration.statusinvest.model.AssetEarningsYearlyModel
import com.fasolutions.finance.integration.statusinvest.model.CompanyProventsResponse

class CompanyProventsMapper {

    fun map(response: CompanyProventsResponse): CompanyProvents {
        return CompanyProvents(
            code = response.code!!,
            yearProvents = mapEarningYears(response.assetEarningsYearlyModels)
        )
    }

    private fun mapEarningYears(yearlyModels: List<AssetEarningsYearlyModel>): Map<Int, Double> {
        val rankMap = yearlyModels.associateBy { it.rank }
        val map = mutableMapOf<Int, Double>()
        rankMap.forEach { rank ->
            map[rank.key] = rank.value.value
        }
        return map
    }
}