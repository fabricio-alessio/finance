package com.fasolutions.finance.integration.statusinvest.mapper

import com.fasolutions.finance.application.model.CompanyPrices
import com.fasolutions.finance.integration.statusinvest.model.CompanyPricesResponse
import com.fasolutions.finance.integration.statusinvest.model.Price
import java.text.SimpleDateFormat

class CompanyPricesMapper {

    companion object {
        val formatter = SimpleDateFormat("dd/MM/yy")
    }

    fun map(response: CompanyPricesResponse): CompanyPrices {
        return CompanyPrices(
            code = response.code!!,
            prices = mapPrices(response.prices)
        )
    }

    private fun mapPrices(prices: List<Price>): List<CompanyPrices.Price> {
        return prices.map {
            CompanyPrices.Price(
                value = it.price,
                date = formatter.parse(it.date.substring(0, 8))
            )
        }
    }
}