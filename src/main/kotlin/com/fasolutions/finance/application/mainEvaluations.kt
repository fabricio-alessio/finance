package com.fasolutions.finance.application

import com.fasolutions.finance.application.ResourceLoader.loadFileAsLines
import com.fasolutions.finance.application.model.CompanyEvaluations
import com.fasolutions.finance.application.persist.CompanyRepository

fun main() {
    // se remover de evaluations.csv tem que remover de companyEvaluations_XXXX.json

    val evaluationsFile = ResourceLoader.getEvaluationsFile()
    val lines = loadFileAsLines(evaluationsFile)
    val repository = CompanyRepository()

    lines.forEach { line ->
        val values = line.split("|")
        val code = values[0]
        val payout = values[1]
        val div23 = values[2]
        val div24 = values[3]
        val div25 = values[4]
        val evaluations = CompanyEvaluations(
            code = code,
            observedPayout = payout.toDouble(),
            proventsPrediction = listOf(
                CompanyEvaluations.Provents(2023, div23.toDouble()),
                CompanyEvaluations.Provents(2024, div24.toDouble()),
                CompanyEvaluations.Provents(2025, div25.toDouble()),
            )
        )
        repository.saveEvaluations(evaluations)
    }
}