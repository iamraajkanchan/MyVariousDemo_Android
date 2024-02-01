package com.example.filesystemdemo.repository

import com.example.filesystemdemo.model.Album

sealed class AlbumState {
    object Loading : AlbumState()
    class Success(val albums: List<Album>) : AlbumState()
    class Failure(val error: Throwable) : AlbumState()
}
