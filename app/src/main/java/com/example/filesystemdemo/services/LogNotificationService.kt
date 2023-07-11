package com.example.filesystemdemo.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.filesystemdemo.R
import com.example.filesystemdemo.utilities.NOTIFICATION_CHANNEL
import com.example.filesystemdemo.utilities.NOTIFICATION_NAME
import com.example.filesystemdemo.utilities.NOTIFICATION_TITLE
import com.example.filesystemdemo.utilities.Utility

private const val LOG_NOTIFICATION_SERVICE_ID = 3

class LogNotificationService : IntentService(LogNotificationService::class.simpleName) {

    private val utility: Utility by lazy { Utility(applicationContext) }

    override fun onHandleIntent(intent: Intent?) {
        showNotification()
    }

    private fun showNotification() {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
            notificationManager.createNotificationChannel(channel)
            val builder = Notification.Builder(applicationContext, NOTIFICATION_CHANNEL)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Logging from LogNotificationService")
                .setStyle(Notification.BigTextStyle().bigText(""))
            builder.build()
        } else {
            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Logging from LogNotificationService")
                .setAutoCancel(false)
            builder.build()
        }
        notificationManager.notify(LOG_NOTIFICATION_SERVICE_ID, notification)
        startForeground(LOG_NOTIFICATION_SERVICE_ID, notification)
    }
}