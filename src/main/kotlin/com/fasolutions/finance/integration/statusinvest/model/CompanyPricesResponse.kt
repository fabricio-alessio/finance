package com.fasolutions.finance.integration.statusinvest.model

data class CompanyPricesResponse(
    var code: String?,
    val prices: List<Price>
)
