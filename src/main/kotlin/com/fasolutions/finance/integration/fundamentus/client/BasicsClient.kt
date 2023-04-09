package com.fasolutions.finance.integration.fundamentus.client

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class BasicsClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .build();

    fun call(code: String): List<String> {

        val url = "https://www.fundamentus.com.br/detalhes.php?papel=$code"
        val request = Request.Builder()
            .url(url)
            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36")
            .get()
            .build();

        val response = client.newCall(request).execute();

        if (response.code == 200) {
            val body = response.body!!.string()
            return body.lines()
        } else {
            throw IOException("Unexpected code ${response.code} url $url and response ${response.body!!.string()}")
        }
    }
}
