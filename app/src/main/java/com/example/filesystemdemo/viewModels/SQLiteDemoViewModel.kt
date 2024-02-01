package com.example.filesystemdemo.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filesystemdemo.db.AppSQLDatabaseImpl
import com.example.filesystemdemo.model.Album
import com.example.filesystemdemo.repository.AlbumState
import com.example.filesystemdemo.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SQLiteDemoViewModel @Inject constructor(
    val repository: AppRepository, val database: AppSQLDatabaseImpl
) : ViewModel() {

    private val _albumState: MutableStateFlow<AlbumState> = MutableStateFlow(AlbumState.Loading)
    val albumState: StateFlow<AlbumState> get() = _albumState

    init {
        viewModelScope.launch {
            getAlbumResponse()
        }
    }

    private suspend fun getAlbumResponse() {
        val responseFlow = repository.getAlbums()
        responseFlow.collectLatest { response ->
            if (response.isSuccessful) {
                _albumState.value = AlbumState.Success(response.body() ?: emptyList())

            } else {
                _albumState.value = AlbumState.Failure(Exception("Server Not Found"))
            }
        }
    }

    fun setAlbums(albums: List<Album>) {
        database.insertAlbum(albums)
    }

    fun getAlbums() = database.getAlbums()

}