package com.fasolutions.finance.application

import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient.Type.THIRTY_DAYS

fun main() {
    val client = CompanyPricesClient()

    val response = client.callForCompany("TAEE11", THIRTY_DAYS)
    println(response)
}