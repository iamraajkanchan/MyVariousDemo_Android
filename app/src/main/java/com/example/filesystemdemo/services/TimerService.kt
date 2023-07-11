package com.example.filesystemdemo.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class TimerService : Service() {

    private val timer = Timer()
    private var seconds = 0
    private var secondsOnScreen = 0
    private var minutes = 0
    private var hours = 0

    override fun onCreate() {
        super.onCreate()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (seconds % 60 == 0) {
                    minutes = seconds / 60
                    secondsOnScreen %= 60;
                    hours = minutes / 60
                }
                seconds++
                secondsOnScreen++
                println("TimerService :: configureTimer :: seconds : $seconds :: minutes : $minutes")
            }
        }, 0, 1000)
    }

    fun getTimerSeconds(): Int = secondsOnScreen
    fun getTimerMinutes(): Int = minutes
    fun getTimerHours(): Int = hours

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        return TimerServiceBinder()
    }

    companion object {
        const val TIMER_SECONDS: String = "timer_seconds"
        const val TIMER_MINUTES: String = "timer_minutes"
        const val TIMER_HOURS: String = "timer_hours"
        const val TIMER_SERVICE_REPLY: String = "timer_service_reply"
        const val TIMER_SERVICE_RESULT_CODE: Int = 123
        const val TIMER_SERVICE_REQUEST_CODE: Int = 122
    }

    inner class TimerServiceBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }
}