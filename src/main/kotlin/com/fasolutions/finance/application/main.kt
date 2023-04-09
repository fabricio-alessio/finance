package com.fasolutions.finance.application

import com.fasolutions.finance.integration.statusinvest.client.CompanyProventsClient
import com.fasolutions.finance.integration.statusinvest.model.CompanyProventsResponse

fun main() {

    val client = CompanyProventsClient()

    val companyCodes = listOf("TAEE11")
    val years = "2018|2019|2020|2021|2022"
    println("code|$years")
    companyCodes.forEach {code ->
        val response = client.callForCompany(code)
        print("$code|")
        years.split("|").forEach { year ->
            print("${byYear(response, year)}|")
        }
        println()
    }
}

fun byYear(provents: CompanyProventsResponse, year: String): Double {
    val earning =  provents.assetEarningsYearlyModels
        .find { earning -> earning.rank == year.toInt() }
    return earning?.value ?: 0.0
}

