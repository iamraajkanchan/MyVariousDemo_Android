package com.example.filesystemdemo.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.filesystemdemo.services.LogNotificationService
import com.example.filesystemdemo.utilities.Utility

class LogNotificationServiceReceiver : BroadcastReceiver() {

    private lateinit var utility: Utility

    override fun onReceive(context: Context?, intent: Intent?) {
        utility = Utility(context!!)
        if (intent?.action == LOG_NOTIFICATION_SERVICE_BROADCAST_ACTION) {
            val logNotificationServiceIntent =
                Intent(context.applicationContext, LogNotificationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(logNotificationServiceIntent)
            } else {
                context.startService(logNotificationServiceIntent)
            }
        }
    }

    companion object {
        const val LOG_NOTIFICATION_SERVICE_BROADCAST_ACTION =
            "log_notification_service_broadcast_action"
    }
}