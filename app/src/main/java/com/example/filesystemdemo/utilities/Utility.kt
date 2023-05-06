package com.example.filesystemdemo.utilities

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class Utility(private val context: Context) {

    private var view: CoordinatorLayout? = null

    constructor(context: Context, view: CoordinatorLayout) : this(context) {
        this.view = view
    }

    fun showSnackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        } ?: Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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

    fun <T> isServiceRunning(kClass: Class<T>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            return kClass.name.equals(service.service.className)
        }
        return false
    }
}