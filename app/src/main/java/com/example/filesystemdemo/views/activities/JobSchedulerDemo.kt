package com.example.filesystemdemo.views.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityJobSchedulerDemoBinding
import com.example.filesystemdemo.jobs.BackgroundPrintLogJob
import java.util.concurrent.TimeUnit

private const val JOB_ID = 988
private const val REFRESH_TIME_INTERVAL = (2 * 60 * 1000).toLong()

class JobSchedulerDemo : AppCompatActivity() {

    private lateinit var binding: ActivityJobSchedulerDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSchedulerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnBackPrintLog.setOnClickListener { scheduleBackgroundPrintJob() }
    }

    private fun scheduleBackgroundPrintJob() {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(this@JobSchedulerDemo, BackgroundPrintLogJob::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setPeriodic(REFRESH_TIME_INTERVAL)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()
        jobScheduler.schedule(jobInfo)
    }
}