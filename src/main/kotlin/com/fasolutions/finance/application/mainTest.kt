package com.fasolutions.finance.application

import com.fasolutions.finance.integration.statusinvest.model.AssetEarningsYearlyModel
import com.fasolutions.finance.integration.statusinvest.model.CompanyProventsResponse

fun main() {

    val earning1 = AssetEarningsYearlyModel(2018, 3.4)
    val response = CompanyProventsResponse(
        code = "TEST",
        assetEarningsYearlyModels = listOf(earning1)
    )

    val value = byYear2(response, "2018")
    println(value)
}


fun byYear2(provents: CompanyProventsResponse, year: String): Double {
    val earning =  provents.assetEarningsYearlyModels
        .find { earning -> earning.rank == year.toInt() }
    return earning?.value ?: 0.0
}