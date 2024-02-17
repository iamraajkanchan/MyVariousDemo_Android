package com.example.filesystemdemo.workers

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DownloadWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val downloadUrl = inputData.getString(DOWNLOAD_URL_KEY)!!
        val fileName = inputData.getString(IMAGE_NAME_KEY)!!
        downloadFile(applicationContext, downloadUrl, fileName)
        return Result.success()
    }

    @SuppressLint("Range")
    private fun downloadFile(context: Context, downloadUrl: String, fileName: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            Toast.makeText(
                context,
                "Download Manager is not available for this version!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val request = DownloadManager.Request(Uri.parse(downloadUrl))
            .setTitle(fileName)
            .setDescription("Downloading...")
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                "$fileName.jpg"
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        /* Testing if this feature is required or not
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val query = DownloadManager.Query()
                query.setFilterById(downloadManager.enqueue(request))
                val cursor = downloadManager.query(query)

                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        Toast.makeText(
                            context,
                            "$fileName is download successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (status == DownloadManager.STATUS_FAILED) {
                        Toast.makeText(
                            context,
                            "Download Failed, please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                cursor.close()

                handler.postDelayed(this, 1000) // Check every second
            }
        }
        handler.post(runnable)
        */
    }
    companion object {
        const val DOWNLOAD_URL_KEY = "download unsplash image key"
        const val IMAGE_NAME_KEY = "name unsplash image key"
    }
}