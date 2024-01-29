package com.example.filesystemdemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel(
    val logId: String,
    val timeStamp: String,
    val api_key_id: String,
    val channel: String,
    val message: String
) : Parcelable
