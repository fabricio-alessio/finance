package com.fasolutions.finance.integration.statusinvest.client

import com.fasolutions.finance.integration.statusinvest.model.CompanyProventsResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class CompanyProventsClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build();

    private val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun callForCompany(companyCode: String): CompanyProventsResponse {

        val url = "https://statusinvest.com.br/acao/companytickerprovents?companyName=sanepar&ticker=$companyCode&chartProventsType=1"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()

        if (response.code == 200) {
            val body = response.body!!.string()
            println(body)
            val proventsResponse = mapper.readValue(body, CompanyProventsResponse::class.java)
            proventsResponse.code = companyCode
            return proventsResponse
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}
