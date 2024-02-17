package com.example.filesystemdemo.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val JSON_PLACEHOLDER_URL = "https://jsonplaceholder.typicode.com/"
    private const val UNSPLASH_URL = "https://api.unsplash.com/"

    private val bodyLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val headerLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }
    private val responseLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    fun getRetrofitInstanceForJSONPlaceHolder(interceptor: Interceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(bodyLoggingInterceptor)
            .addInterceptor(headerLoggingInterceptor)
            .addInterceptor(responseLoggingInterceptor)
            .build()
        return Retrofit.Builder().baseUrl(JSON_PLACEHOLDER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getRetrofitInstanceForUnsplash(interceptor: Interceptor): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(bodyLoggingInterceptor)
            .addInterceptor(headerLoggingInterceptor)
            .addInterceptor(responseLoggingInterceptor)
            .build()
        return Retrofit.Builder().baseUrl(UNSPLASH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}