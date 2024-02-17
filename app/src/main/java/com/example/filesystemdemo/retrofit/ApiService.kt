package com.example.filesystemdemo.retrofit

import com.example.filesystemdemo.model.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    // @Headers("Cache-Control: max-age=3600")
    @GET("albums")
    suspend fun getAlbums() : Response<List<Album>>

}