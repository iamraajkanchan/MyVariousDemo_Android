package com.example.filesystemdemo.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.example.filesystemdemo.databinding.ActivityFileSystemBinding
import com.example.filesystemdemo.utilities.ARG_FILE_NAME
import com.example.filesystemdemo.utilities.ARG_FOLDER_NAME
import com.example.filesystemdemo.utilities.Utility
import java.io.File
import java.io.FileWriter
import java.io.IOException

class FileSystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileSystemBinding
    private lateinit var downloadFolder: File
    private lateinit var file: File
    private lateinit var utility: Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileSystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        downloadFolder = ActivityCompat.getExternalFilesDirs(
            this@FileSystemActivity, Environment.DIRECTORY_DOWNLOADS
        )[0]
        utility = Utility(binding.fileSystemCoordinator)
        file = File(downloadFolder.absolutePath, "personal.txt")
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateFolder.setOnClickListener { createDownloadFolder() }
        binding.btnCreateFile.setOnClickListener { createFileInDownload() }
        binding.btnReadFile.setOnClickListener { goToReadFile() }
        binding.btnWriteFile.setOnClickListener { writeInFile() }
    }

    private fun createDownloadFolder() {
        if (!downloadFolder.exists()) {
            if (downloadFolder.mkdir()) {
                utility.showSnackBar("${downloadFolder.name} Created")
            }
        }
    }

    private fun createFileInDownload() {
        downloadFolder = ActivityCompat.getExternalFilesDirs(
            this@FileSystemActivity, Environment.DIRECTORY_DOWNLOADS
        )[0]
        if (!downloadFolder.exists()) {
            if (downloadFolder.mkdir()) {
                utility.showSnackBar("${downloadFolder.name} Created")
            }
        } else {
            if (file.exists()) {
                utility.showSnackBar("${file.name} already exists")
            } else {
                if (file.createNewFile()) {
                    utility.showSnackBar("${file.name} created")
                }
            }
        }
    }

    private fun writeInFile() {
        if (binding.edtFileContent.text.isNotEmpty()) {
            if (file.exists()) {
                try {
                    val writer = FileWriter(file, true)
                    writer.append("${binding.edtFileContent.text}\n")
                    writer.flush()
                    writer.close()
                    utility.showSnackBar("Completed!")
                } catch (e: IOException) {
                    utility.showSnackBar(e.message ?: "Something went wrong!!!")
                }
            } else {
                if (file.createNewFile()) {
                    utility.showSnackBar("${file.name} created")
                }
            }
        } else {
            utility.showSnackBar("Please write something to write")
        }
    }

    private fun goToReadFile() {
        Intent(this@FileSystemActivity, ReadFileActivity::class.java).apply {
            putExtra(ARG_FILE_NAME, file.name)
            putExtra(ARG_FOLDER_NAME, Environment.DIRECTORY_DOWNLOADS)
            startActivity(this)
        }
    }
}