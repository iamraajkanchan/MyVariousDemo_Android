package com.example.filesystemdemo.retrofit

import com.example.filesystemdemo.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("albums")
    suspend fun getAlbums() : Response<List<Album>>
}