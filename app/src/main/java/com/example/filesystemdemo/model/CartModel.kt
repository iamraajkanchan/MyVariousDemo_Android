package com.example.filesystemdemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartModel(
    var isPaid: Boolean,
    val itemName: String?,
    val itemPrice: Double
) : Parcelable