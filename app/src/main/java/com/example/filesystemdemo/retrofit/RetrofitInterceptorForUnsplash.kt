package com.example.filesystemdemo.retrofit

import com.example.filesystemdemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptorForUnsplash : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID ${BuildConfig.UNSPLASH_ACESS_KEY}")
            .build()
        return chain.proceed(request)
    }
}