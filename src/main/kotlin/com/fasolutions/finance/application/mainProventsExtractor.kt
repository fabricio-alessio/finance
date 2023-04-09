package com.fasolutions.finance.application

import com.fasolutions.finance.application.ResourceLoader.getAllCompanyCodesFile
import com.fasolutions.finance.application.ResourceLoader.getMyCompanyCodesFile
import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.statusinvest.client.CompanyProventsClient
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyProventsMapper
import java.lang.Thread.sleep

fun main() {

    val mapper = CompanyProventsMapper()
    val repository = CompanyRepository()
    val client = CompanyProventsClient()

    val companyCodes = getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting provents of $code")
        val response = client.callForCompany(code)
        val companyProvents = mapper.map(response)
        repository.saveProvents(companyProvents)
        sleep(1000)
    }
}
