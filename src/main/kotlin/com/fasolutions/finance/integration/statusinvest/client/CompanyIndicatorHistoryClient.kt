package com.fasolutions.finance.integration.statusinvest.client

import com.fasolutions.finance.integration.statusinvest.model.CompanyIndicatorHistoryResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class CompanyIndicatorHistoryClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build();

    private val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun callForCompany(companyCode: String): CompanyIndicatorHistoryResponse {
        val url = "https://statusinvest.com.br/acao/indicatorhistoricallist"
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("codes[]", companyCode)
            .addFormDataPart("time", "5")
            .addFormDataPart("byQuarter", "false")
            .addFormDataPart("futureData", "false")
            .build()
        val request = Request.Builder()
            .url(url)
            .method("POST", body)
            .build()

        val response = client.newCall(request).execute()

        if (response.code == 200) {
            val body = response.body!!.string()
            println(body)
            return mapper.readValue(body, CompanyIndicatorHistoryResponse::class.java)
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}
