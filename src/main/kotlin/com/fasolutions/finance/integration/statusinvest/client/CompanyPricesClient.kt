package com.fasolutions.finance.integration.statusinvest.client

import com.fasolutions.finance.integration.statusinvest.model.CompanyPricesResponse
import com.fasolutions.finance.integration.statusinvest.model.CompanyPricesResponseEnvelope
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class CompanyPricesClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build();

    private val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    enum class Type(val code: String){
        DAY("-1"), FIVE_DAYS("0"), THIRTY_DAYS("1"), SIX_MONTHS("2"), ONE_YEAR("3"), FIVE_YEARS("4")
    }

    fun callForCompany(companyCode: String, type: Type): CompanyPricesResponse {
        val url = "https://statusinvest.com.br/acao/tickerprice"
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("ticker", companyCode)
            .addFormDataPart("type", type.code)
            .addFormDataPart("currences[]", "1")
            .build()
        val request = Request.Builder()
            .url(url)
            .method("POST", body)
            .build()

        val response = client.newCall(request).execute()

        if (response.code == 200) {
            val body = response.body!!.string()
            println(body)
            val envelope = "{ \"list\": $body }"
            val envelopeResponse = mapper.readValue(envelope, CompanyPricesResponseEnvelope::class.java)
            val priceResponse = envelopeResponse.list[0]
            priceResponse.code = companyCode
            return priceResponse
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}