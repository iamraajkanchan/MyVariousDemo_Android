package com.example.filesystemdemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(val userId: String, val id: String, val title: String) :
    Parcelable