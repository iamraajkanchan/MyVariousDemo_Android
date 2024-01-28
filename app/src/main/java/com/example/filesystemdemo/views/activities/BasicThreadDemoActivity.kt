package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityBasicThreadDemoBinding
import com.example.filesystemdemo.utilities.Utility

class BasicThreadDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicThreadDemoBinding
    private val utility = Utility(this@BasicThreadDemoActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicThreadDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configuration()
    }

    private fun configuration() {
        binding.btnMultiplicationUsingThread.setOnClickListener {
            utility.goTo(MultiplicationUsingThreadActivity::class.java)
        }
        binding.btnMultiplicationUsingAsyncTask.setOnClickListener {
            utility.goTo(MultiplicationUsingAsyncTaskActivity::class.java)
        }
    }
}