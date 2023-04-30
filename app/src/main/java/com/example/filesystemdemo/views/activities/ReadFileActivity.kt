package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.filesystemdemo.databinding.ActivityReadFileBinding
import com.example.filesystemdemo.utilities.ARG_FILE_NAME
import com.example.filesystemdemo.utilities.ARG_FOLDER_NAME
import com.example.filesystemdemo.utilities.TAG
import com.example.filesystemdemo.utilities.Utility
import java.io.File
import java.io.FileReader

class ReadFileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadFileBinding
    private lateinit var utility: Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        utility = Utility(binding.readFileCoordinator)
        readFile()
    }

    private fun readFile() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            println("$TAG :: Running my code on - ${Thread.currentThread().name} thread")
            if (intent.hasExtra(ARG_FOLDER_NAME) && intent.hasExtra(ARG_FILE_NAME)) {
                val downloadFolder = ActivityCompat.getExternalFilesDirs(
                    this@ReadFileActivity, intent.getStringExtra(ARG_FOLDER_NAME)
                )[0]
                val file = File(downloadFolder.absolutePath, intent.getStringExtra(ARG_FILE_NAME))
                if (file.exists()) {
                    val reader = FileReader(file)
                    if (reader.ready()) {
                        binding.tvContent.text = reader.readText()
                    } else {
                        utility.showSnackBar("Something went wrong while reading the file!!!")
                    }
                } else {
                    utility.showSnackBar("${file.name} doesn't exist")
                }
            }
        }
    }
}