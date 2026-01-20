package com.ybelik.data.remote


import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}
