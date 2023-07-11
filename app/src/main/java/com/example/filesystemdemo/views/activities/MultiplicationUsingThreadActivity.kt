package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityMultiplicatoinUsingThreadBinding
import com.example.filesystemdemo.utilities.MultiplicationCalculator
import com.google.android.material.snackbar.Snackbar

class MultiplicationUsingThreadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplicatoinUsingThreadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplicatoinUsingThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnCalculate.setOnClickListener {
            if (!binding.edtNumber.text.isNullOrBlank()) {
                val thread =
                    Thread(
                        MultiplicationCalculator(binding.edtNumber.text.toString().toInt(), binding)
                    )
                thread.start()
                runOnUiThread {
                    if (thread.state == Thread.State.TERMINATED) {
                        binding.edtNumber.setText("")
                    }
                }
            } else {
                Snackbar.make(
                    binding.coordinator, "Please Enter a valid number!!!", Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}