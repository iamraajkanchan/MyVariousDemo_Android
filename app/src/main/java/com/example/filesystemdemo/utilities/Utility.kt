package com.example.filesystemdemo.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class Utility(private val context: Context) {

    private lateinit var view: CoordinatorLayout

    constructor(context: Context, view: CoordinatorLayout) : this(context) {
        this.view = view
    }

    fun showSnackBar(message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.ENGLISH)
        return timeFormat.format(Calendar.getInstance().time)
    }

    fun vectorToBitmap(drawableId: Int): Bitmap? {
        val drawable = AppCompatResources.getDrawable(context, drawableId) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}