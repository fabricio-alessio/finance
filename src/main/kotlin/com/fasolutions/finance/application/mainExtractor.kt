package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.nuinvest.client.CustodyPositionClient
import com.fasolutions.finance.integration.nuinvest.mapper.CompanyPositionsMapper
import com.fasolutions.finance.integration.statusinvest.client.CompanyIndicatorHistoryClient
import com.fasolutions.finance.integration.statusinvest.client.CompanyPricesClient
import com.fasolutions.finance.integration.statusinvest.client.CompanyProventsClient
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyIndicatorHistoryMapper
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyPriceMapper
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyPricesMapper
import com.fasolutions.finance.integration.statusinvest.mapper.CompanyProventsMapper

fun main() {
    // https://www.nuinvest.com.br/acompanhar/investimentos -> filter custody-position -> get authorization (Bearer xxxx)
    val bearer = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE2ODAyNjg1ODQsImV4cCI6MTY4MDI5NzM4NCwiaXNzIjoiaHR0cHM6Ly9hcGkuZWFzeW52ZXN0LmNvbS5ici9hdXRoIiwiYXVkIjpbImh0dHBzOi8vYXBpLmVhc3ludmVzdC5jb20uYnIvYXV0aC9yZXNvdXJjZXMiLCJodHRwczovL3d3dy5lYXN5bnZlc3QuY29tLmJyIl0sImNsaWVudF9pZCI6Ijg3NmRhYjIxOTA0NjQ4ODRiZjliMDkyYWExNDA3NTg1Iiwic3ViIjoiOTA5NDIyMTM5MDQiLCJhdXRoX3RpbWUiOjE2ODAyNjg1ODQsImlkcCI6Imh0dHBzOi8vYXBpLmVhc3ludmVzdC5jb20uYnIvYXV0aCIsInVuaXF1ZV9uYW1lIjoiOTA5NDIyMTM5MDQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjkwOTQyMjEzOTA0IiwibmFtZWlkIjoiOTA5NDIyMTM5MDQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJGYWJyaWNpbyBBbGVzc2lvIiwiZ2l2ZW5fbmFtZSI6IkZhYnJpY2lvIEFsZXNzaW8iLCJ2ZXJzaW9uIjoidjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJGQUJSSUNJTy5PLkFMRVNTSU9AR01BSUwuQ09NIiwiZW1haWwiOiJGQUJSSUNJTy5PLkFMRVNTSU9AR01BSUwuQ09NIiwiYWNjIjoiNTIzMjA4NiIsImFkdiI6IjgwIiwiZXN0ayI6Imluc3RhbGxlZCIsInRyZHYiOmZhbHNlLCJsZ3RwIjoiZWFzeXRva2VuIiwiZXNrdG4iOiJhbHBoYW51bWVyaWMiLCJsb2dpbl9tZXRob2QiOjMsImNsaSI6IntcIkNsaWVudElkXCI6XCI4NzZkYWIyMTkwNDY0ODg0YmY5YjA5MmFhMTQwNzU4NVwiLFwiTmFtZVwiOlwiUG9ydGFsL0hvbWUgQnJva2VyXCIsXCJJbnRlcm5hbFwiOmZhbHNlfSIsImNpYSI6Ijo6ZmZmZjoxMC42NS43MC4yNTAiLCJkZXZpY2VfaWQiOiI2ZGQ3ZTcxOGZmZWRkNDdjZTc3YTlmMjA2NTNjNDNlZSIsInNjb3BlIjpbIm9wZW5pZCIsImh0dHBzOi8vd3d3LmVhc3ludmVzdC5jb20uYnIiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicGFzc3dvcmQiXX0.A4QRhUjq2PGM43H5eP4wi4tzuQn4xnLevDm7oAlvliM"

    extractPositions(bearer)
    extractIndicators()
}

fun extractIndicators() {
    val indicatorMapper = CompanyIndicatorHistoryMapper()
    val priceMapper = CompanyPriceMapper()
    val pricesMapper = CompanyPricesMapper()
    val proventsMapper = CompanyProventsMapper()

    val indicatorClient = CompanyIndicatorHistoryClient()
    val pricesClient = CompanyPricesClient()
    val proventsClient = CompanyProventsClient()

    val repository = CompanyRepository()

    val companyCodes = ResourceLoader.getAllCompanyCodesFile().readLines()
    companyCodes.forEach {code ->
        println("Extracting for $code")
        val indicatorResponse = indicatorClient.callForCompany(code)
        val indicatorHistory = indicatorMapper.map(indicatorResponse)
        repository.saveIndicatorHistory(indicatorHistory)

        val fiveDaysResponse = pricesClient.callForCompany(code, CompanyPricesClient.Type.FIVE_DAYS)
        val companyPrice = priceMapper.map(fiveDaysResponse)
        repository.savePrice(companyPrice)

        val sixMonthsResponse = pricesClient.callForCompany(code, CompanyPricesClient.Type.SIX_MONTHS)
        val companyPrices = pricesMapper.map(sixMonthsResponse)
        repository.savePrices(companyPrices)

        val proventsResponse = proventsClient.callForCompany(code)
        val companyProvents = proventsMapper.map(proventsResponse)
        repository.saveProvents(companyProvents)

        Thread.sleep(1000)
    }
}

fun extractPositions(bearer: String) {
    val client = CustodyPositionClient()
    val response = client.call(bearer)

    val positionsMapper = CompanyPositionsMapper()
    val companyRepository = CompanyRepository()

    val listOfCompanyPositions = positionsMapper.map(response)

    listOfCompanyPositions.forEach { companyPositions ->
        println("Extracting company positions of ${companyPositions.code}")
        val companyPositionsLoaded = companyRepository.loadPositions(companyPositions.code)
        companyPositionsLoaded.changePositionByDate(companyPositions.positions.first())
        companyRepository.savePositions(companyPositionsLoaded)
    }
}