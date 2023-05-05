package com.example.filesystemdemo.workers

import android.app.Notification
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val LOG_NOTIFICATION_ID: Int = 23
private const val LOG_CHANNEL_ID: String = "LogNotificationChannel"

class LogCoroutineWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(LOG_NOTIFICATION_ID, createNotification())
    }

    override suspend fun doWork(): Result {
        return try {
            val folder: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!folder.exists()) {
                folder.mkdir()
            } else {
                val logFile: File = File(folder.absolutePath, "FileSystemLog.txt")
                if (!logFile.exists()) {
                    if (logFile.createNewFile()) {
                        writeInFile(logFile)
                    } else {
                        Result.failure()
                    }
                } else {
                    writeInFile(logFile)
                }
            }
            Result.success()
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure()
        }
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

    private fun createNotification(): Notification {
        val notification =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val builder: Notification.Builder =
                    Notification.Builder(context, LOG_CHANNEL_ID).apply {
                        setContentTitle("Log Service")
                        setContentText("${getCurrentTime()} :: Logging From WorkManager")
                        style = Notification.BigTextStyle().bigText("")
                        setChannelId(LOG_CHANNEL_ID)
                        setAutoCancel(false)
                    }
                builder.build()
            } else {
                val builder = NotificationCompat.Builder(context, LOG_CHANNEL_ID).apply {
                    setContentTitle("Log Service")
                    setContentText("${getCurrentTime()} :: Logging From WorkManager")
                    setChannelId(LOG_CHANNEL_ID)
                    setAutoCancel(false)
                }
                builder.build()
            }
        return notification
    }

}