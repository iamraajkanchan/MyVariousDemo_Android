package com.example.filesystemdemo.utilities

import androidx.viewbinding.ViewBinding
import com.example.filesystemdemo.databinding.ActivityMultiplicatoinUsingThreadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MultiplicationCalculator(private val number: Long, private val binding: ViewBinding) :
    Runnable {
    override fun run() {
        if (binding is ActivityMultiplicatoinUsingThreadBinding) {
            with(binding) {
                for (i in 1 until 12) {
                    if (i < 11) {
                        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                            edtNumber.isEnabled = false
                            tvValue.text = "$number * $i = ${number * i}"
                        }
                        TimeUnit.SECONDS.sleep(3)
                    } else {
                        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                            edtNumber.isEnabled = true
                            edtNumber.setText("")
                        }
                    }
                }
            }
        }
    }
}