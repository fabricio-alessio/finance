package com.fasolutions.finance.integration.nuinvest.model

data class CustodyPositionResponse(
    val investments: List<Investment>
) {

    data class Investment(
        val stockCode: String?,
        val financialCurrentValue: Double,
        val lastPrice: Double,
        val totalQuantity: Double,
        val averagePrice: Double,
        val securityType: String,
        val investmentType: InvestmentType
    )

    data class InvestmentType(
        val id: Int
    )
}
