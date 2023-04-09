package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient.Type.FIVE_DAYS
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyPriceMapper

fun main() {

    val mapper = CompanyPriceMapper()
    val repository = CompanyRepository()
    val client = CompanyPricesClient()

    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting prices of $code")
        val response = client.callForCompany(code, FIVE_DAYS)
        val companyPrice = mapper.map(response)
        repository.savePrice(companyPrice)
        Thread.sleep(500)
    }
}