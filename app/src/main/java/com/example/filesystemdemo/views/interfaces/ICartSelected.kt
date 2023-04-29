package com.example.filesystemdemo.views.interfaces

import com.example.filesystemdemo.model.CartModel

interface ICartSelected {
    fun selectedCart(cart: CartModel)
    fun removedCart(cart: CartModel)
}