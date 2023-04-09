package com.fasolutions.finance.application

import com.fasolutions.finance.application.persist.CompanyRepository
import com.fasolutions.finance.integration.nuinvest.client.CustodyPositionClient
import com.fasolutions.finance.integration.nuinvest.mapper.CompanyPositionsMapper
import com.fasolutions.finance.integration.nuinvest.model.CustodyPositionResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun main() {
    //val json = ResourceLoader.getCustodyPositionFile().readText(Charsets.UTF_8)
    //val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    //val response = mapper.readValue(json, CustodyPositionResponse::class.java)

    // to extract current position in nu-invest

    // https://www.nuinvest.com.br/acompanhar/investimentos -> filter custody-position -> get authorization (Bearer xxxx)
    val bearer = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE2NzU2MDU5MDEsImV4cCI6MTY3NTYzNDcwMSwiaXNzIjoiaHR0cHM6Ly9hcGkuZWFzeW52ZXN0LmNvbS5ici9hdXRoIiwiYXVkIjpbImh0dHBzOi8vYXBpLmVhc3ludmVzdC5jb20uYnIvYXV0aC9yZXNvdXJjZXMiLCJodHRwczovL3d3dy5lYXN5bnZlc3QuY29tLmJyIl0sImNsaWVudF9pZCI6Ijg3NmRhYjIxOTA0NjQ4ODRiZjliMDkyYWExNDA3NTg1Iiwic3ViIjoiOTA5NDIyMTM5MDQiLCJhdXRoX3RpbWUiOjE2NzU2MDU5MDEsImlkcCI6Imh0dHBzOi8vYXBpLmVhc3ludmVzdC5jb20uYnIvYXV0aCIsInVuaXF1ZV9uYW1lIjoiOTA5NDIyMTM5MDQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjkwOTQyMjEzOTA0IiwibmFtZWlkIjoiOTA5NDIyMTM5MDQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJGYWJyaWNpbyBBbGVzc2lvIiwiZ2l2ZW5fbmFtZSI6IkZhYnJpY2lvIEFsZXNzaW8iLCJ2ZXJzaW9uIjoidjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJGQUJSSUNJTy5PLkFMRVNTSU9AR01BSUwuQ09NIiwiZW1haWwiOiJGQUJSSUNJTy5PLkFMRVNTSU9AR01BSUwuQ09NIiwiYWNjIjoiNTIzMjA4NiIsImFkdiI6IjgwIiwiZXN0ayI6Imluc3RhbGxlZCIsInRyZHYiOnRydWUsImxndHAiOiJlYXN5dG9rZW4iLCJlc2t0biI6ImFscGhhbnVtZXJpYyIsImxvZ2luX21ldGhvZCI6MywiY2xpIjoie1wiQ2xpZW50SWRcIjpcIjg3NmRhYjIxOTA0NjQ4ODRiZjliMDkyYWExNDA3NTg1XCIsXCJOYW1lXCI6XCJQb3J0YWwvSG9tZSBCcm9rZXJcIixcIkludGVybmFsXCI6ZmFsc2V9IiwiY2lhIjoiOjpmZmZmOjEwLjY1LjkyLjE4MyIsImRldmljZV9pZCI6IjZkZDdlNzE4ZmZlZGQ0N2NlNzdhOWYyMDY1M2M0M2VlIiwic2NvcGUiOlsib3BlbmlkIiwiaHR0cHM6Ly93d3cuZWFzeW52ZXN0LmNvbS5iciIsIm9mZmxpbmVfYWNjZXNzIl0sImFtciI6WyJwYXNzd29yZCJdfQ.xCzYvqUjmIsS_2X0nqg5fFtU5D0imYsFxtuU_Cq6EMU"
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