package com.example.filesystemdemo.retrofit

import com.example.filesystemdemo.model.UnsplashImageResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface UnsplashService {
    @GET("/photos")
    suspend fun getUnsplashImages() : Response<List<UnsplashImageResponseItem>>
}