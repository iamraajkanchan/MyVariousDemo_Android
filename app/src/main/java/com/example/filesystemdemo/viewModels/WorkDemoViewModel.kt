package com.example.filesystemdemo.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filesystemdemo.repository.AppRepository
import com.example.filesystemdemo.repository.UnsplashImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WorkDemoViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {
    private val _unsplashImageState: MutableStateFlow<UnsplashImageState> =
        MutableStateFlow(UnsplashImageState.Loading)
    val unsplashImageState get() = _unsplashImageState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUnsplashImages()
        }
    }

    private suspend fun getUnsplashImages() {
        appRepository.getUnsplashImages().collectLatest { response ->
            if (response.isSuccessful) {
                _unsplashImageState.value =
                    UnsplashImageState.Success(response.body() ?: emptyList())
            } else {
                _unsplashImageState.value =
                    UnsplashImageState.Failure(Exception("Server Not Found!"))
            }
        }
    }
}