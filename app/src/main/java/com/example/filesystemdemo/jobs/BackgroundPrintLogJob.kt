package com.example.filesystemdemo.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import androidx.work.Configuration

class BackgroundPrintLogJob : JobService() {

    init {
        val builder = Configuration.Builder()
        builder.setJobSchedulerJobIdRange(0, 1000).build()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        println("BackgroundPrintLogJob :: onStartJob :: Running...")
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        println("BackgroundPrintLogJob :: onStopJob :: Running...")
        return false
    }
}