package com.example.filesystemdemo.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filesystemdemo.databinding.ActivityScanBarcodeDemoBinding
import com.example.filesystemdemo.views.fragments.ScanBarcodeFragment
import com.example.filesystemdemo.views.interfaces.IScanBarcode


class ScanBarcodeDemo : AppCompatActivity(), IScanBarcode {

    private lateinit var binding: ActivityScanBarcodeDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarcodeDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanBarcode.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(binding.fcvScanBarcode.id, ScanBarcodeFragment(this)).commit()
        }
    }

    override fun getBarcodeValue(content: String) {
        binding.tvBarcodeValue.text = content
    }
}