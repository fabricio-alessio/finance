package com.fasolutions.finance.integration.nuinvest.mapper

import com.fasolutions.finance.application.model.CompanyPositions
import com.fasolutions.finance.application.model.SimpleDate
import com.fasolutions.finance.integration.nuinvest.model.CustodyPositionResponse

class CompanyPositionsMapper {

    companion object {
        const val STOCK_TYPE_ID = 7
    }

    fun map(positionResponse: CustodyPositionResponse): List<CompanyPositions> {
        return positionResponse.investments
            .filter(this::isStock)
            .map(this::mapInvestment)
    }

    private fun mapInvestment(investment: CustodyPositionResponse.Investment) =
        CompanyPositions(
            code = investment.stockCode!!,
            positions = mutableListOf(
                CompanyPositions.Position(
                    currentPrice = investment.lastPrice,
                    totalQuantity = investment.totalQuantity.toInt(),
                    averagePrice = investment.averagePrice,
                    date = SimpleDate.now()
                )
            )
        )

    private fun isStock(investment: CustodyPositionResponse.Investment): Boolean {
        return investment.stockCode != null && investment.investmentType.id == STOCK_TYPE_ID
    }
}