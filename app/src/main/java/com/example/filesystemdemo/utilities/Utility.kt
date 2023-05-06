package com.example.filesystemdemo.utilities

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class Utility() {

    private lateinit var view: CoordinatorLayout

    constructor(view: CoordinatorLayout) : this() {
        this.view = view
    }

    fun showSnackBar(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.ENGLISH)
        return timeFormat.format(Calendar.getInstance().time)
    }
}