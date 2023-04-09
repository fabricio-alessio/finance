package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.statusinvest.client.CompanyIndicatorHistoryClient
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyIndicatorHistoryMapper
import java.lang.Thread.sleep

fun main() {

    val mapper = CompanyIndicatorHistoryMapper()
    val repository = CompanyRepository()
    val client = CompanyIndicatorHistoryClient()

    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting indicators of $code")
        val response = client.callForCompany(code)
        val companyIndicatorHistory = mapper.map(response)
        repository.saveIndicatorHistory(companyIndicatorHistory)
        sleep(1000)
    }
}
