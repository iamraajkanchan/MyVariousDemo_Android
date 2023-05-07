package com.example.filesystemdemo.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.filesystemdemo.utilities.Utility
class LogNotificationReceiver : BroadcastReceiver() {

    private lateinit var utility: Utility

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { utility = Utility(it) }
        if (intent?.action == "log_notification_receiver") {
            utility.showSnackBar("LogNotificationReceiver is working.")
        }
    }
}