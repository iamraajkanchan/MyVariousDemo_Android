package com.example.filesystemdemo.views.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityAlarmManagerDemoBinding
import com.example.filesystemdemo.services.LogNotificationService
import com.example.filesystemdemo.utilities.Utility
import java.util.concurrent.TimeUnit

private const val ALARM_CODE = 0

class AlarmManagerDemo : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmManagerDemoBinding
    private lateinit var utility: Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmManagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        utility = Utility(this@AlarmManagerDemo)
        binding.btnInfiniteBackgroundService.setOnClickListener { runService() }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun runService() {
        val logNotificationServiceIntent =
            Intent(this@AlarmManagerDemo, LogNotificationService::class.java)
        if (!utility.isServiceRunning(LogNotificationService::class.java)) {
            startService(logNotificationServiceIntent)
        }
    }

    private fun runAlarmManager() {
        val broadcastIntent = Intent("restart_service")
        val intervals = TimeUnit.MINUTES.toMinutes(5)
        val triggerIntervals = System.currentTimeMillis() + intervals
        val operation: PendingIntent = PendingIntent.getBroadcast(
            this@AlarmManagerDemo,
            ALARM_CODE,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerIntervals,
            intervals,
            operation
        )
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        // To Intentionally Kill A Service.
        /*
         if (utility.isServiceRunning(LogNotificationService::class.java)) {
             Intent(this@AlarmManagerDemo, LogNotificationService::class.java).apply {
                 stopService(this)
             }
         }
         */
        super.onDestroy()
    }
}