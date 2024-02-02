package com.example.filesystemdemo.retrofit

import android.content.Context
import com.example.filesystemdemo.utilities.Utility
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val utility = Utility(context)
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        if (!utility.isNetworkConnected()) {
            val offLineRequest = request.newBuilder()
                .addHeader("X-Offline", "true")
                .build()
            return chain.proceed(offLineRequest)
        }
        return chain.proceed(request)
    }
}