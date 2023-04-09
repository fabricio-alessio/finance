package com.fasolutions.finance.application.model

data class CompanyEvaluations(
    val code: String,
    val observedPayout: Double,
    val proventsPrediction: List<Provents>
) {
    data class Provents(
        val year: Int,
        val value: Double
    )

    fun averageNextProvents(): Double {
        return if (proventsPrediction.isEmpty()) {
            0.0
        } else {
            proventsPrediction.sumOf { it.value } / proventsPrediction.size
        }
    }
}
