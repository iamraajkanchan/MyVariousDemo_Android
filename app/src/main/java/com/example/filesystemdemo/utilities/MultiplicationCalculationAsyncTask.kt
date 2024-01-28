package com.example.filesystemdemo.utilities

import android.os.AsyncTask
import androidx.viewbinding.ViewBinding
import com.example.filesystemdemo.databinding.ActivityMultiplicationUsingAsyncTaskBinding

class MultiplicationCalculationAsyncTask(
    private val number: Long,
    private val multiplier: Int,
    private val binding: ViewBinding
) :
    AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val result = number * multiplier;
        return "$number * $multiplier = $result"
    }

    override fun onProgressUpdate(vararg values: Void?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: String) {
        println("Result : $result")
        if (binding is ActivityMultiplicationUsingAsyncTaskBinding) {
            binding.tvValue.text = result
        }
    }

}