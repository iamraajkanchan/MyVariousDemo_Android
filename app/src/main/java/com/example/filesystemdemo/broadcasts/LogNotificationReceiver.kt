package com.example.filesystemdemo.broadcasts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.filesystemdemo.R
import com.example.filesystemdemo.utilities.NOTIFICATION_CHANNEL
import com.example.filesystemdemo.utilities.NOTIFICATION_NAME
import com.example.filesystemdemo.utilities.NOTIFICATION_TITLE
import com.example.filesystemdemo.utilities.Utility

private const val LOG_NOTIFICATION_RECEIVER_ID = 4
const val LOG_NOTIFICATION_RECEIVER_ACTION = "com.example.filesystemdemo.log_notification_receiver"
const val LOG_NOTIFICATION_BUNDLE = "extra bundle"

class LogNotificationReceiver : BroadcastReceiver() {

    private lateinit var utility: Utility

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { utility = Utility(it) }
        if (intent?.action == LOG_NOTIFICATION_RECEIVER_ACTION) {
            println("LogNotificationReceiver :: onReceive")
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_MIN
                )
                notificationManager.createNotificationChannel(channel)
                val builder = Notification.Builder(context, NOTIFICATION_CHANNEL)
                    .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                    .setSmallIcon(R.drawable.icon_delete)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText("${utility.getCurrentTime()} Logging from LogNotificationReceiver")
                    .setStyle(Notification.BigTextStyle().bigText(""))
                    .setAutoCancel(false)
                builder.build()
            } else {
                val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                    .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                    .setSmallIcon(R.drawable.icon_delete)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText("${utility.getCurrentTime()} Logging from LogNotificationReceiver")
                    .setAutoCancel(false)
                builder.build()
            }
            notificationManager.notify(LOG_NOTIFICATION_RECEIVER_ID, notification)
            utility.showSnackBar("LogNotificationReceiver is working.")
        }
    }
}