package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityViewPagerDemoWithFragmentBinding

class ViewPagerDemoActivityWithFragment : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerDemoWithFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerDemoWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}