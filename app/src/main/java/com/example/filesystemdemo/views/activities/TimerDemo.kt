package com.example.filesystemdemo.views.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.filesystemdemo.databinding.ActivityTimerDemoBinding
import com.example.filesystemdemo.services.TimerService
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class TimerDemo : AppCompatActivity() {

    private lateinit var binding: ActivityTimerDemoBinding
    private lateinit var timer: Timer
    private lateinit var countDownTimer: CountDownTimer
    private var seconds = 0
    private var secondsOnScreen = 0
    private var minutes = 0
    private var hours = 0
    private var isTimerServiceBounded = false
    private var timerService: TimerService? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service?.isBinderAlive!!) {
                val binder = service as TimerService.TimerServiceBinder
                timerService = binder.getService()
                isTimerServiceBounded = true
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isTimerServiceBounded = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // configureTimer()
        configureCountDownTimer()
        startTimerService()
    }

    private fun configureTimer() {
        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        if (seconds % 60 == 0) {
                            binding.tvTimerValue.text =
                                "${String.format("%02d", hours)} :" +
                                        " ${String.format("%02d", minutes)} :" +
                                        " ${String.format("%02d", secondsOnScreen)}"
                            minutes = seconds / 60
                            secondsOnScreen %= 60;
                            hours = minutes / 60
                        }
                        seconds++
                        secondsOnScreen++
                        println("TimerDemo :: configureTimer :: seconds : $seconds :: minutes : $minutes")
                        binding.tvTimerValue.text =
                            "${String.format("%02d", hours)} :" +
                                    " ${String.format("%02d", minutes)} :" +
                                    " ${String.format("%02d", secondsOnScreen)}"
                    }
                }
            }, 0, 1000)
        }
    }

    private fun configureCountDownTimer() {
        countDownTimer = object : CountDownTimer(100000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val numberFormat = DecimalFormat("00", DecimalFormatSymbols(Locale.ENGLISH))
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.tvCountDownTimerValue.text =
                    "${numberFormat.format(hour)} : " +
                            "${numberFormat.format(min)} : ${numberFormat.format(sec)}"
            }

            override fun onFinish() {
                binding.tvCountDownTimerValue.text = "00 : 00 : 00"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        countDownTimer.start()
        binding.btnStopTimer.setOnClickListener { timer.cancel() }
        binding.tvCountDownTimerValue.setOnClickListener { countDownTimer.cancel() }
    }

    private fun startTimerService() {
        Intent(this@TimerDemo, TimerService::class.java).also {
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    binding.tvTimerValue.text =
                        "${String.format("%02d", timerService?.getTimerHours())} :" +
                                " ${
                                    String.format("%02d", timerService?.getTimerMinutes())
                                } :" +
                                " ${
                                    String.format("%02d", timerService?.getTimerSeconds())
                                }"
                }
            }
        }, 0, 1000)
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TimerService.TIMER_SERVICE_REQUEST_CODE && resultCode == TimerService.TIMER_SERVICE_RESULT_CODE) {
            binding.tvTimerValue.text =
                "${String.format("%02d", data?.extras?.getInt(TimerService.TIMER_HOURS))} :" +
                        " ${
                            String.format(
                                "%02d", data?.extras?.getInt(TimerService.TIMER_MINUTES)
                            )
                        } :" +
                        " ${
                            String.format(
                                "%02d", data?.extras?.getInt(TimerService.TIMER_SECONDS)
                            )
                        }"
        }
    }
    */

    override fun onStop() {
        super.onStop()
        isTimerServiceBounded = false
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }
}