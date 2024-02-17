package com.example.filesystemdemo.workers

import androidx.work.ListenableWorker
import java.lang.Exception

class AsyncApi {
    interface OnResult {
        fun onSuccess(result: ListenableWorker.Result)
        fun onFailure(exception: Exception)
    }

    fun load(onResult: OnResult) {}
}