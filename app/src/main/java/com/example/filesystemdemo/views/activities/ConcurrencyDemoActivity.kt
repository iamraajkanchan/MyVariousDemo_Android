package com.example.filesystemdemo.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityConcurrencyDemoBinding

class ConcurrencyDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcurrencyDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcurrencyDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnBasicThreadDemo.setOnClickListener {
            Intent(this@ConcurrencyDemoActivity, BasicThreadDemoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(this)
            }
        }
    }
}