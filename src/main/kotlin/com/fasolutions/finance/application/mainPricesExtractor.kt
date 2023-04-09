package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient.Type.SIX_MONTHS
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyPriceMapper
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyPricesMapper

fun main() {

    val mapper = CompanyPricesMapper()
    val repository = CompanyRepository()
    val client = CompanyPricesClient()

    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting prices of $code")
        val response = client.callForCompany(code, SIX_MONTHS)
        val companyPrices = mapper.map(response)
        repository.savePrices(companyPrices)
        Thread.sleep(500)
    }
}