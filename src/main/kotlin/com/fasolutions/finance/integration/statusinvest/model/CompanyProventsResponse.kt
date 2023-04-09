package com.fasolutions.finance.integration.statusinvest.model

data class CompanyProventsResponse(
    var code: String?,
    val assetEarningsYearlyModels: List<AssetEarningsYearlyModel>
)
