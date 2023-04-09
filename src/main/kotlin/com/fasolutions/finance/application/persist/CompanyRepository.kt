package com.fasolutions.finance.application.persist

import com.fasolutions.finance.application.model.CompanyBasics
import com.fasolutions.finance.application.model.CompanyEvaluations
import com.fasolutions.finance.application.model.CompanyIndicatorHistory
import com.fasolutions.finance.application.model.CompanyPositions
import com.fasolutions.finance.application.model.CompanyPrice
import com.fasolutions.finance.application.model.CompanyPrices
import com.fasolutions.finance.application.model.CompanyProvents
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.io.FileNotFoundException

class CompanyRepository {
    val path = "/Users/falessio/finance"
    private val mapper = jacksonObjectMapper()

    fun saveProvents(companyProvents: CompanyProvents) {
        val text = mapper.writeValueAsString(companyProvents)
        fileProvents(companyProvents.code).writeText(text)
    }

    fun loadProvents(companyCode: String): CompanyProvents {
        val text = fileProvents(companyCode).readText(Charsets.UTF_8)
        return mapper.readValue(text, CompanyProvents::class.java)
    }

    fun saveEvaluations(companyEvaluations: CompanyEvaluations) {
        val text = mapper.writeValueAsString(companyEvaluations)
        fileEvaluations(companyEvaluations.code).writeText(text)
    }

    fun loadEvaluations(companyCode: String): CompanyEvaluations {
        val text = fileAsTextOrEmpty(fileEvaluations(companyCode))
        return if (text.isEmpty()) {
            CompanyEvaluations(
                code = companyCode,
                observedPayout = 0.0,
                proventsPrediction = emptyList()
            )
        } else {
            mapper.readValue(text, CompanyEvaluations::class.java)
        }
    }

    fun savePrice(companyPrice: CompanyPrice) {
        val text = mapper.writeValueAsString(companyPrice)
        filePrice(companyPrice.code).writeText(text)
    }

    fun loadPrice(companyCode: String): CompanyPrice {
        val text = filePrice(companyCode).readText(Charsets.UTF_8)
        return mapper.readValue(text, CompanyPrice::class.java)
    }

    fun savePrices(companyPrices: CompanyPrices) {
        val text = mapper.writeValueAsString(companyPrices)
        filePrices(companyPrices.code).writeText(text)
    }

    fun loadPrices(companyCode: String): CompanyPrices {
        val text = filePrices(companyCode).readText(Charsets.UTF_8)
        return mapper.readValue(text, CompanyPrices::class.java)
    }

    fun saveIndicatorHistory(companyIndicatorHistory: CompanyIndicatorHistory) {
        val text = mapper.writeValueAsString(companyIndicatorHistory)
        fileIndicatorHistory(companyIndicatorHistory.code).writeText(text)
    }

    fun loadIndicatorHistory(companyCode: String): CompanyIndicatorHistory {
        val text = fileIndicatorHistory(companyCode).readText(Charsets.UTF_8)
        return mapper.readValue(text, CompanyIndicatorHistory::class.java)
    }

    fun savePositions(companyPositions: CompanyPositions) {
        val text = mapper.writeValueAsString(companyPositions)
        filePositions(companyPositions.code).writeText(text)
    }

    fun loadBasics(companyCode: String): CompanyBasics {
        val text = fileBasics(companyCode).readText(Charsets.UTF_8)
        return mapper.readValue(text, CompanyBasics::class.java)
    }

    fun saveBasics(companyBasics: CompanyBasics) {
        val text = mapper.writeValueAsString(companyBasics)
        fileBasics(companyBasics.code).writeText(text)
    }

    fun loadPositions(companyCode: String): CompanyPositions {
        val text = filePositionsAsTextOrEmpty(companyCode)
        return if (text.isEmpty()) {
            CompanyPositions(
                code = companyCode,
                positions = mutableListOf()
            )
        } else {
            mapper.readValue(text, CompanyPositions::class.java)
        }
    }

    private fun fileProvents(companyCode: String) = File("$path/companyProvents_${companyCode}.json")

    private fun fileEvaluations(companyCode: String) = File("$path/companyEvaluations_${companyCode}.json")

    private fun filePrice(companyCode: String) = File("$path/companyPrice_${companyCode}.json")

    private fun filePrices(companyCode: String) = File("$path/companyPrices_${companyCode}.json")

    private fun fileIndicatorHistory(companyCode: String) = File("$path/companyIndicatorHistory_${companyCode}.json")

    private fun filePositions(companyCode: String) = File("$path/companyPositions_${companyCode}.json")

    private fun fileBasics(companyCode: String) = File("$path/companyBasics_${companyCode}.json")

    private fun filePositionsAsTextOrEmpty(companyCode: String): String {
        return try {
            filePositions(companyCode).readText(Charsets.UTF_8)
        } catch (ex: FileNotFoundException) {
            ""
        }
    }

    private fun fileAsTextOrEmpty(file: File): String {
        return try {
            file.readText(Charsets.UTF_8)
        } catch (ex: FileNotFoundException) {
            ""
        }
    }
}