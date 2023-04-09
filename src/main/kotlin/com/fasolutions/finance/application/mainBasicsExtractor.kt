package com.fasolutions.finance.application

import com.fasolutions.finance.integration.fundamentus.client.BasicsClient
import com.fasolutions.finance.integration.fundamentus.mapper.BasicsMapper

fun main() {

    val client = BasicsClient()
    //val htmlFile = ResourceLoader.getFundamentusFile()
    //val lines = loadFileAsLines(htmlFile)
    val mapper = BasicsMapper()

    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting basic of $code")
        val response = client.call(code)
        val companyBasics = mapper.map(response)
        repository.saveBasics(companyBasics)
        Thread.sleep(1000)
    }
}
