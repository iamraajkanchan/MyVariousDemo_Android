package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filesystemdemo.databinding.ActivityMultiplicationUsingAsyncTaskBinding
import com.example.filesystemdemo.utilities.MultiplicationCalculationAsyncTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MultiplicationUsingAsyncTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplicationUsingAsyncTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplicationUsingAsyncTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureButtons()
    }

    private fun configureButtons() {
        binding.btnCalculate.setOnClickListener {
            if (!binding.edtNumber.text.isNullOrBlank()) {
                for (i in 1 until 11) {
                    CoroutineScope(Dispatchers.Default).launch {
                        MultiplicationCalculationAsyncTask(
                            binding.edtNumber.text.toString().toLong(), i, binding
                        ).execute()
                        delay(TimeUnit.SECONDS.toMillis(3))
                    }
                }
            }
        }
    }
}