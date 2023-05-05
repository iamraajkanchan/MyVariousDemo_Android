package com.example.filesystemdemo.workers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.filesystemdemo.R
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_NOTIFICATION_ID: Int = 0

class LogWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(LOG_NOTIFICATION_ID, Notification())
    }

    override suspend fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(LOG_NOTIFICATION_ID, createNotification(notificationManager))
        return Result.success()
    }

    private fun writeInFile(file: File) {
        val writer: FileWriter = FileWriter(file, true)
        writer.append(getCurrentTime()).append(" ").append("Logging from WorkManager").append("\n")
        writer.flush()
        writer.close()
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
                        setLargeIcon(vectorToBitmap(R.drawable.ic_launcher_foreground))
                        setSmallIcon(R.drawable.icon_delete)
                        setContentTitle("FileSystem Log Service")
                        setContentText("${getCurrentTime()} :: Logging From WorkManager")
                        style = Notification.BigTextStyle().bigText("")
                        setChannelId(NOTIFICATION_CHANNEL)
                        setAutoCancel(false)
                    }
                notificationManager.createNotificationChannel(channel)
                builder.build()
            } else {
                val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL).apply {
                    setLargeIcon(vectorToBitmap(R.drawable.ic_launcher_foreground))
                    setSmallIcon(R.drawable.icon_delete)
                    setContentTitle("Log Service")
                    setContentText("${getCurrentTime()} :: Logging From WorkManager")
                    setChannelId(NOTIFICATION_CHANNEL)
                    setAutoCancel(false)
                }
                builder.build()
            }
        return notification
    }

    companion object {
        const val NOTIFICATION_ID = "FileSystem_notification_id"
        const val NOTIFICATION_NAME = "FileSystem"
        const val NOTIFICATION_CHANNEL = "FileSystem_channel_01"
        const val NOTIFICATION_WORK = "FileSystem_notification_work"
    }

    fun vectorToBitmap(drawableId: Int): Bitmap? {
        val drawable = AppCompatResources.getDrawable(context, drawableId) ?: return null
        val bitmap = createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}