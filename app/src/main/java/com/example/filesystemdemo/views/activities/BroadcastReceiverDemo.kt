package com.example.filesystemdemo.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.broadcasts.LogNotificationReceiver
import com.example.filesystemdemo.databinding.ActivityBroadCastReceiverDemoBinding

class BroadcastReceiverDemo : AppCompatActivity() {

    private lateinit var binding: ActivityBroadCastReceiverDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadCastReceiverDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnSendCustomBroadcast.setOnClickListener { sendCustomBroadcast() }
    }

    private fun sendCustomBroadcast() {
        val intent = Intent().apply {
            action = "log_notification_receiver"
            setClass(this@BroadcastReceiverDemo, LogNotificationReceiver::class.java)
        }
        sendBroadcast(intent)
    }
}