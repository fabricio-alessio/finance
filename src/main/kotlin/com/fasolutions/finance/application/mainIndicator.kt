package com.fasolutions.finance.application

import com.fasolutions.finance.integration.statusinvest.client.CompanyIndicatorHistoryClient
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyIndicatorHistoryMapper
import com.fasolutions.finance.integration.statusinvest.model.CompanyIndicatorHistoryResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun main() {
    val client = CompanyIndicatorHistoryClient()

   // val response = client.callForCompany("TAEE11")
    val json = ResourceLoader.getIndicatorHistoryFile().readText(Charsets.UTF_8)
    val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val response = mapper.readValue(json, CompanyIndicatorHistoryResponse::class.java)

    println(response)

    response.data["taeE11"]!!.forEach { indicator ->
        println(indicator.key)
    }

    val indicatorHistory = CompanyIndicatorHistoryMapper().map(response)
    println(indicatorHistory)
}