package com.fasolutions.finance.integration.statusinvest.mapper

import com.fasolutions.finance.application.model.CompanyPrice
import com.fasolutions.finance.integration.statusinvest.model.CompanyPricesResponse
import com.fasolutions.finance.integration.statusinvest.model.Price

class CompanyPriceMapper {

    fun map(response: CompanyPricesResponse): CompanyPrice {
        return CompanyPrice(
            code = response.code!!,
            price = mapPrice(response.prices)
        )
    }

    private fun mapPrice(prices: List<Price>): Double {
        return try {
            prices.last().price
        } catch (ex: NoSuchElementException) {
            0.0
        }

    }
}