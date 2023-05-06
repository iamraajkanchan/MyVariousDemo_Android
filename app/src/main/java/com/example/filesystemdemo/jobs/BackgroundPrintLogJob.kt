package com.example.filesystemdemo.jobs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Configuration
import com.example.filesystemdemo.R
import com.example.filesystemdemo.utilities.*

private const val JOB_NOTIFICATION_ID = 1

class BackgroundPrintLogJob : JobService() {

    private val utility: Utility by lazy { Utility(baseContext) }

    init {
        val builder = Configuration.Builder()
        builder.setJobSchedulerJobIdRange(900, 1000).build()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        println("BackgroundPrintLogJob :: onStartJob :: Running...")
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
            val builder = Notification.Builder(baseContext, NOTIFICATION_CHANNEL)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Logging from JobScheduler")
                .setAutoCancel(false)
                .setStyle(Notification.BigTextStyle().bigText(""))
            notificationManager.createNotificationChannel(channel)
            builder.build()
        } else {
            val builder = NotificationCompat.Builder(baseContext, NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentText(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Logging from JobScheduler")
                .setAutoCancel(false)
            builder.build()
        }
        notificationManager.notify(JOB_NOTIFICATION_ID, notification)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        println("BackgroundPrintLogJob :: onStopJob :: Running...")
        return false
    }
}