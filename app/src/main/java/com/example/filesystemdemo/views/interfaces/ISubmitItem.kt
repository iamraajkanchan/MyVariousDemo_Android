package com.example.filesystemdemo.views.interfaces

import com.example.filesystemdemo.model.CartModel

interface ISubmitItem {
    fun addItemIntoCart(items: ArrayList<CartModel>)
}