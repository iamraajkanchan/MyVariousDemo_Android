package com.example.filesystemdemo.repository

import com.example.filesystemdemo.model.UnsplashImageResponseItem

sealed class UnsplashImageState {
    object Loading : UnsplashImageState()
    class Success(val images: List<UnsplashImageResponseItem>) : UnsplashImageState()
    class Failure(val error: Throwable) : UnsplashImageState()
}