package com.example.filesystemdemo.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.filesystemdemo.services.LocationNotificationService
import com.example.filesystemdemo.services.LogNotificationService
import com.example.filesystemdemo.utilities.Utility

class LocationNotificationReceiver : BroadcastReceiver() {

    private lateinit var utility: Utility

    override fun onReceive(context: Context?, intent: Intent?) {
        utility = Utility(context!!)
        println("LocationNotificationReceiver :: onReceive :: Running...")
        if (intent?.action == LOCATION_NOTIFICATION_BROADCAST_ACTION) {
            val locationNotificationServiceIntent =
                Intent(context.applicationContext, LocationNotificationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(locationNotificationServiceIntent)
            } else {
                context.startService(locationNotificationServiceIntent)
            }
        }
    }

    companion object {
        const val LOCATION_NOTIFICATION_BROADCAST_ACTION =
            "location_notification_broadcast_action"
    }
}