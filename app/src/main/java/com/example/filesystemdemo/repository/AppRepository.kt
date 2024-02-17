package com.example.filesystemdemo.repository

import com.example.filesystemdemo.model.Album
import com.example.filesystemdemo.model.UnsplashImageResponseItem
import com.example.filesystemdemo.retrofit.ApiService
import com.example.filesystemdemo.retrofit.UnsplashService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class AppRepository(
    private val apiService: ApiService,
    private val unsplashService: UnsplashService
) {
    suspend fun getAlbums(): Flow<Response<List<Album>>> = flow {
        emit(apiService.getAlbums())
    }.flowOn(Dispatchers.IO)

    suspend fun getUnsplashImages(): Flow<Response<List<UnsplashImageResponseItem>>> = flow {
        emit(unsplashService.getUnsplashImages())
    }.flowOn(Dispatchers.IO)
}