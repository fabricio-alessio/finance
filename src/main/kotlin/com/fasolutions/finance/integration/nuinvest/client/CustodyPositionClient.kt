package com.fasolutions.finance.integration.nuinvest.client

import com.fasolutions.finance.integration.nuinvest.model.CustodyPositionResponse
import com.fasolutions.finance.integration.statusinvest.model.CompanyProventsResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class CustodyPositionClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build();

    private val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun call(bearer: String): CustodyPositionResponse {

        val url = "https://www.nuinvest.com.br/api/samwise/v2/custody-position"
        val request = Request.Builder()
            .url(url)
            .get()
            .header("authorization", bearer)
            .build()

        val response = client.newCall(request).execute()

        if (response.code == 200) {
            val body = response.body!!.string()
            println(body)
            return mapper.readValue(body, CustodyPositionResponse::class.java)
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}
