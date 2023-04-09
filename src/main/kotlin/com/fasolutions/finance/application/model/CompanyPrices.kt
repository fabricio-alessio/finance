package com.fasolutions.finance.application.model

import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

data class CompanyPrices(
    val code: String,
    val prices: List<Price>
) {
    data class Price(
        val value: Double,
        val date: Date
    )

    fun averageOfMonthsAgo(count: Long): Double {
        var mydate = LocalDate.now()
        mydate = mydate.minusMonths(count)
        val countMonthsAgo = Date.from(mydate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        val listOfCountMonthsAgo = prices.filter {
            it.date > countMonthsAgo
        }
        val sum = listOfCountMonthsAgo.sumOf { it.value }
        val count = listOfCountMonthsAgo.size
        return sum / count
    }

    fun nearAtCountDaysAgo(count: Long): Double {
        var value = atCountDaysAgo(count)
        var c = count;
        while (value == 0.0 && c > 0) {
            c--
            value = atCountDaysAgo(c)
        }
        return value
    }

    fun atCountDaysAgo(count: Long): Double {
        val daysAgo = dateCountDaysAgo(count)
        return priceAtDate(daysAgo)
    }

    private fun dateCountDaysAgo(count: Long): Date {
        val now = LocalDate.now()
        val countDaysAgo = now.minusDays(count)
        return Date.from(countDaysAgo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private fun priceAtDate(date: Date): Double {
        return prices.find { price -> price.date == date }?.value ?: 0.0
    }
}