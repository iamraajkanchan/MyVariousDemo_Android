package com.example.filesystemdemo.utilities

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

class Utility(private val view: CoordinatorLayout) {

    fun showSnackBar(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}