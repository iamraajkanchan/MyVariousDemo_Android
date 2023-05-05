package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.filesystemdemo.databinding.ActivityWorkManagerDemoBinding
import com.example.filesystemdemo.workers.LogWorker
import java.util.concurrent.TimeUnit

class WorkManagerDemo : AppCompatActivity() {

    private lateinit var binding: ActivityWorkManagerDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnBackgroundCheck.setOnClickListener { startWorkManager() }
    }

    private fun startWorkManager() {
        val periodicWork = PeriodicWorkRequestBuilder<LogWorker>(2, TimeUnit.MINUTES).build()
        val workManager = WorkManager.getInstance(this@WorkManagerDemo)
        workManager.enqueue(periodicWork)
    }
}