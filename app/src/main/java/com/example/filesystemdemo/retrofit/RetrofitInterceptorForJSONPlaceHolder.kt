package com.example.filesystemdemo.retrofit

import com.example.filesystemdemo.utilities.NetworkManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RetrofitInterceptorForJSONPlaceHolder : Interceptor {
    @Inject
    lateinit var networkManager: NetworkManager
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        if (networkManager.isNetworkAvailable) {
            val offLineRequest = request.newBuilder()
                .addHeader("X-Offline", "true")
                .build()
            return chain.proceed(offLineRequest)
        }
        return chain.proceed(request)
    }
}