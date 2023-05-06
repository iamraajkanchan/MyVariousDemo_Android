package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.filesystemdemo.databinding.ActivityReadFileBinding
import com.example.filesystemdemo.utilities.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class ReadFileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadFileBinding
    private lateinit var utility: Utility
    private lateinit var downloadFolder: File
    private lateinit var file: File
    private lateinit var readLooper: ApplicationLooper
    private lateinit var clearLooper: ApplicationLooper
    private lateinit var readHandler: ApplicationHandler
    private lateinit var clearHandler: ApplicationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        downloadFolder = ActivityCompat.getExternalFilesDirs(
            this@ReadFileActivity, intent.getStringExtra(ARG_FOLDER_NAME)
        )[0]
        utility = Utility(this@ReadFileActivity, binding.readFileCoordinator)
        readLooper = ApplicationLooper()
        readLooper.start()
        readLooper.waitForStart()
        readHandler = ApplicationHandler(readLooper.getLooper() ?: Looper.getMainLooper())
        clearLooper = ApplicationLooper()
        clearLooper.start()
        clearLooper.waitForStart()
        clearHandler = ApplicationHandler(clearLooper.getLooper() ?: Looper.getMainLooper())
        readFile()
    }

    override fun onStart() {
        super.onStart()
        binding.fabDelete.setOnClickListener { clearFile() }
    }

    @Synchronized
    private fun readFile() {
        readHandler.post {
            println("$TAG :: readFile() is running on - ${Thread.currentThread().name} thread")
            if (intent.hasExtra(ARG_FOLDER_NAME) && intent.hasExtra(ARG_FILE_NAME)) {
                file = File(downloadFolder.absolutePath, intent.getStringExtra(ARG_FILE_NAME))
                if (file.exists()) {
                    val reader = FileReader(file)
                    if (reader.ready()) {
                        binding.tvContent.text = reader.readText()
                        reader.close()
                    } else {
                        utility.showSnackBar(EMPTY_FILE_ERROR)
                    }
                } else {
                    utility.showSnackBar("${file.name} doesn't exist")
                }
            }
        }
    }

    @Synchronized
    private fun clearFile() {
        clearHandler.post {
            println("$TAG :: clearFile() is running on - ${Thread.currentThread().name} thread")
            if (intent.hasExtra(ARG_FOLDER_NAME)) {
                if (intent.hasExtra(ARG_FILE_NAME)) {
                    if (file.exists()) {
                        val writer = FileWriter(file)
                        writer.write("")
                        writer.flush()
                        writer.close()
                        readFile()
                    } else {
                        utility.showSnackBar(FILE_NOT_EXIST_ERROR)
                    }
                } else {
                    utility.showSnackBar(FILE_NOT_EXIST_ERROR)
                }
            } else {
                utility.showSnackBar(FOLDER_NOT_EXIST_ERROR)
            }
        }
    }
}