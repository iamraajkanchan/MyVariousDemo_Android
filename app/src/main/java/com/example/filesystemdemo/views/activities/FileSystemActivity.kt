package com.example.filesystemdemo.views.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filesystemdemo.databinding.ActivityFileSystemBinding
import java.io.File

class FileSystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileSystemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileSystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateFolder.setOnClickListener {
           val folders = ActivityCompat.getExternalFilesDirs(
                this@FileSystemActivity, Environment.DIRECTORY_DOWNLOADS
            )

        }
    }
}