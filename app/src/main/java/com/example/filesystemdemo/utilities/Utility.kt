package com.example.filesystemdemo.utilities

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

const val SOCKET_URL = "wss://s11684.blr1.piesocket.com/v3/1?api_key=mraIHJnDRY0pSU3EUEVp35zyndI9s7lZZjHIA5zY&notify_self=1"

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
    fun <T> goTo(clazz: Class<T>) {
        Intent(context, clazz).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(this)
        }
    }
    fun <T> isServiceRunning(kClass: Class<T>): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            return kClass.name.equals(service.service.className)
        }
        return false
    }


    fun isNetworkConnected() : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.isDefaultNetworkActive
    }
}