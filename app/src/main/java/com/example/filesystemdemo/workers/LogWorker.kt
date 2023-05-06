package com.example.filesystemdemo.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.filesystemdemo.R
import com.example.filesystemdemo.utilities.*
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_NOTIFICATION_ID: Int = 0

class LogWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val utility: Utility by lazy { Utility(context) }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(LOG_NOTIFICATION_ID, Notification())
    }

    override suspend fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(LOG_NOTIFICATION_ID, createNotification(notificationManager))
        return Result.success()
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("dd:MM:yy HH:mm:ss", Locale.ENGLISH)
        return timeFormat.format(Calendar.getInstance().time)
    }

    private fun createNotification(notificationManager: NotificationManager): Notification {
        val notification =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL, NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_MIN
                ).apply {
                    enableVibration(true)
                    enableLights(true)
                    vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                }
                val builder: Notification.Builder =
                    Notification.Builder(context, NOTIFICATION_CHANNEL).apply {
                        setChannelId(NOTIFICATION_CHANNEL)
                        setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                        setSmallIcon(R.drawable.icon_delete)
                        setContentTitle(NOTIFICATION_TITLE)
                        setContentText("${getCurrentTime()} :: Logging From WorkManager")
                        setAutoCancel(false)
                        style = Notification.BigTextStyle().bigText("")
                    }
                notificationManager.createNotificationChannel(channel)
                builder.build()
            } else {
                val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL).apply {
                    setChannelId(NOTIFICATION_CHANNEL)
                    setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                    setSmallIcon(R.drawable.icon_delete)
                    setContentTitle("Log Service")
                    setContentText("${getCurrentTime()} :: Logging From WorkManager")
                    setAutoCancel(false)
                }
                builder.build()
            }
        return notification
    }

}