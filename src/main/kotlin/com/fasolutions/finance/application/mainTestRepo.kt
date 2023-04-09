package com.fasolutions.finance.application

import com.fasolutions.finance.application.model.CompanyProvents
import com.fasolutions.finance.application.persist.CompanyRepository


fun main() {
    val repository = CompanyRepository()
    val companyProvents = CompanyProvents("TEST", mapOf(2018 to 3.4, 2019 to 45.3))
    repository.saveProvents(companyProvents)

    val readProvents = repository.loadProvents("TEST")
    println(readProvents)
}
