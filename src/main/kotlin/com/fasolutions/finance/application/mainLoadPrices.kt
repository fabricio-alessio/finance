package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository

fun main() {
    val repository = CompanyRepository()
    val prices = repository.loadPrices("TAEE11")
    println(prices)
    prices.prices.sortedBy { it.date }.forEach { println(it.date) }

    val avg = prices.averageOfMonthsAgo(6)
    println(avg)
}