package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.filesystemdemo.databinding.ActivityWorkManagerDemoBinding
import com.example.filesystemdemo.repository.UnsplashImageState
import com.example.filesystemdemo.utilities.Utility
import com.example.filesystemdemo.viewModels.WorkDemoViewModel
import com.example.filesystemdemo.views.adapters.UnsplashImageAdapter
import com.example.filesystemdemo.workers.LogWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WorkManagerDemo : AppCompatActivity() {

    private lateinit var binding: ActivityWorkManagerDemoBinding
    private val viewModel by viewModels<WorkDemoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUnsplashImages()
    }

    private fun getUnsplashImages() {
        lifecycleScope.launch {
            viewModel.unsplashImageState.collectLatest { state ->
                when (state) {
                    is UnsplashImageState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UnsplashImageState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val adapter = UnsplashImageAdapter(this@WorkManagerDemo, state.images)
                        binding.recyclerView.adapter = adapter
                    }

                    is UnsplashImageState.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Utility(
                            this@WorkManagerDemo,
                            binding.coordinator
                        ).showSnackBar(state.error.message ?: "Server Not Found")
                    }
                }
            }
        }
    }

    private fun startWorkManager() {
        val periodicWork = PeriodicWorkRequestBuilder<LogWorker>(2, TimeUnit.MINUTES).build()
        val workManager = WorkManager.getInstance(this@WorkManagerDemo)
        workManager.enqueue(periodicWork)
    }
}