package com.example.filesystemdemo.utilities

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

const val TAG: String = "FileSystemDemo"

fun showSnackBar(view: CoordinatorLayout, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}