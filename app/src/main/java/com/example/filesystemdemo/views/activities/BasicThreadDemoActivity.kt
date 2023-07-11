package com.example.filesystemdemo.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityBasicThreadDemoBinding

class BasicThreadDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicThreadDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicThreadDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnMultiplicationUsingThread.setOnClickListener {
            Intent(
                this@BasicThreadDemoActivity,
                MultiplicationUsingThreadActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(this)
            }
        }
    }
}