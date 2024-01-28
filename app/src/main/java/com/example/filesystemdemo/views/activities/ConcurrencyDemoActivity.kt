package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityConcurrencyDemoBinding
import com.example.filesystemdemo.utilities.Utility

class ConcurrencyDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcurrencyDemoBinding
    private val utility = Utility(this@ConcurrencyDemoActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcurrencyDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnBasicThreadDemo.setOnClickListener { utility.goTo(BasicThreadDemoActivity::class.java) }
    }
}