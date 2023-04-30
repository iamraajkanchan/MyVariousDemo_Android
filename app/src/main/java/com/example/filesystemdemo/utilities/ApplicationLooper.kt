package com.example.filesystemdemo.utilities

import android.os.Looper


class ApplicationLooper : Thread() {
    private var started: Boolean = false
    private val startMonitor: Any = Any()
    private var looper: Looper? = null

    override fun run() {
        println("$TAG :: ApplicationLooper is running on ${currentThread().name} thread")
        Looper.prepare()
        looper = Looper.myLooper()
        synchronized(startMonitor) {
            started = true
            (startMonitor as java.lang.Object).notifyAll()
        }
        Looper.loop()
    }

    fun getLooper(): Looper? = looper
    fun waitForStart() {
        synchronized(startMonitor) {
            while (!started) {
                try {
                    (startMonitor as java.lang.Object).wait(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}