package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository

fun main() {

    val repository = CompanyRepository()
    val companyCodes = listOf("TAEE11")

    val years = "2018|2019|2020|2021|2022"
    println("code|$years|")
    companyCodes.forEach {code ->
        val provents = repository.loadProvents(code)
        print("$code|")
        years.split("|").forEach { year ->
            print("${provents.yearProvents[year.toInt()]}|")
        }
        println()
    }
}
