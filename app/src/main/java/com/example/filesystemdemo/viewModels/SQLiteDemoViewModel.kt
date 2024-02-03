package com.example.filesystemdemo.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filesystemdemo.db.AppSQLDatabaseImpl
import com.example.filesystemdemo.model.Album
import com.example.filesystemdemo.repository.AlbumState
import com.example.filesystemdemo.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SQLiteDemoViewModel @Inject constructor(
    private val repository: AppRepository,
    private val databaseImpl: AppSQLDatabaseImpl
) : ViewModel() {

    private val _albumState: MutableStateFlow<AlbumState> = MutableStateFlow(AlbumState.Loading)
    val albumState: StateFlow<AlbumState> get() = _albumState


    suspend fun getAlbumResponse() = viewModelScope.async(Dispatchers.IO) {
        val responseFlow = repository.getAlbums()
        responseFlow.collectLatest { response ->
            if (response.isSuccessful) {
                setAlbums(response.body() ?: emptyList())
            }
        }
    }


    private fun setAlbums(albums: List<Album>) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseImpl.insertAlbum(albums)
        }
    }

    fun getAlbums() {
        viewModelScope.launch {
            databaseImpl.getAlbums().collectLatest { albums ->
                if (albums.isEmpty()) {
                    _albumState.value = AlbumState.Failure(Exception("Server Not Found"))
                } else {
                    _albumState.value = AlbumState.Success(albums)
                }
            }
        }
    }

}