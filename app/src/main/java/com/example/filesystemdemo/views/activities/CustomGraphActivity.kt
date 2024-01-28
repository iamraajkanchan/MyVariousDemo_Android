package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.R
import com.example.filesystemdemo.databinding.ActivityCustomGraphBinding

class CustomGraphActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dgvCustomGraph.updateData(listOf(10f, 25f, 50f, 75f))
        binding.dgvCustomGraph.addDataPoint(45f)
        binding.btnAddDataPoint.setOnClickListener {
            binding.dgvCustomGraph.addDataPoint(75f)
        }
    }
}