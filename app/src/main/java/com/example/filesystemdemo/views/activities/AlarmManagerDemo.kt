package com.example.filesystemdemo.views.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.filesystemdemo.broadcasts.LOG_NOTIFICATION_RECEIVER_ACTION
import com.example.filesystemdemo.broadcasts.LogNotificationReceiver
import com.example.filesystemdemo.broadcasts.LogNotificationServiceReceiver
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

    }

    override fun onStart() {
        binding.btnLogNotificationBroadcastAlarm.setOnClickListener { runAlarmManagerForLogNotificationReceiver() }
        binding.btnInfiniteBackgroundService.setOnClickListener { runAlarmManagerForLogNotificationServiceReceiver() }
        super.onStart()
    }

    private fun runAlarmManagerForLogNotificationReceiver() {
        val broadcastIntent =
            Intent(this@AlarmManagerDemo, LogNotificationReceiver::class.java).apply {
                action = LOG_NOTIFICATION_RECEIVER_ACTION
            }
        val intervals = TimeUnit.MINUTES.toMillis(5)
        println("AlarmManagerDemo :: runAlarmManagerForLogNotificationReceiver :: intervals :: $intervals")
        val triggerIntervals = System.currentTimeMillis() + intervals
        val operation: PendingIntent = PendingIntent.getBroadcast(
            this@AlarmManagerDemo,
            ALARM_CODE,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerIntervals,
            intervals,
            operation
        )
    }

    private fun runAlarmManagerForLogNotificationServiceReceiver() {
        val broadcastIntent =
            Intent(this@AlarmManagerDemo, LogNotificationServiceReceiver::class.java).apply {
                action = LogNotificationServiceReceiver.LOG_NOTIFICATION_SERVICE_BROADCAST_ACTION
            }
        val intervals = TimeUnit.MINUTES.toMillis(5L)
        val timeTriggers = System.currentTimeMillis() + intervals
        val operation: PendingIntent = PendingIntent.getBroadcast(
            this@AlarmManagerDemo,
            ALARM_CODE,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeTriggers, intervals, operation)
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