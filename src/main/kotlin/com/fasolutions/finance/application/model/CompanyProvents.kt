package com.fasolutions.finance.application.model

data class CompanyProvents(
    val code: String,
    val yearProvents: Map<Int, Double>
) {
    fun averageLastYears(years: Int): Double {
        return lastYears(years).average()
    }

    fun lastYears(years: Int): List<Double> {
        val values = valuesOrdered()
        if (values.size < years) {
            val list = mutableListOf<Double>()
            (0 until years - values.size).forEach { _ ->
                list.add(0.0)
            }
            values.forEach { v ->
                list.add(v)
            }
            return list
        } else if (values.size > years) {
            val list = mutableListOf<Double>()
            (values.size - years until values.size).forEach() { i ->
                list.add(values[i])
            }
            return list
        } else {
            return values
        }
    }

    private fun valuesOrdered(): List<Double> {
        return yearProvents.entries.toList().sortedBy { it.key }.map { it.value }
    }
}
