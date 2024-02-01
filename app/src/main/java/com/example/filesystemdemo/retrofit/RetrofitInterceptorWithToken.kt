package com.example.filesystemdemo.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptorWithToken(val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Auth", token)
            .build()
        return chain.proceed(request)
    }
}