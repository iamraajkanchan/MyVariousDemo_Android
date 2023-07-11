package com.example.filesystemdemo.utilities

import androidx.viewbinding.ViewBinding
import com.example.filesystemdemo.databinding.ActivityMultiplicatoinUsingThreadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MultiplicationCalculator(private val number: Int, private val binding: ViewBinding) :
    Runnable {
    override fun run() {
        for (i in 1 until 11) {
            println("$number * $i = ${number * i}")
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                if (binding is ActivityMultiplicatoinUsingThreadBinding) {
                    binding.tvValue.text = (number * i).toString()
                }
            }
            TimeUnit.SECONDS.sleep(3)
        }
    }
}