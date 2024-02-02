package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.filesystemdemo.databinding.ActivitySqliteDemoBinding
import com.example.filesystemdemo.repository.AlbumState
import com.example.filesystemdemo.utilities.Utility
import com.example.filesystemdemo.viewModels.SQLiteDemoViewModel
import com.example.filesystemdemo.views.adapters.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SQLiteDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqliteDemoBinding
    private val viewModel by viewModels<SQLiteDemoViewModel>()
    private val utility = Utility(this@SQLiteDemoActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAlbums()
    }

    private fun getAlbums() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (utility.isNetworkConnected()) {
                viewModel.getAlbumResponse().await()
                viewModel.getAlbums()
            } else {
                viewModel.getAlbums()
            }
            viewModel.albumState.collectLatest { state ->
                when (state) {
                    is AlbumState.Loading -> {
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rcvAlbums.visibility = View.GONE
                        }
                    }

                    is AlbumState.Success -> {
                        for (album in state.albums) {
                            println("SQLiteDemoActivity :: title : ${album.title}")
                        }
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.rcvAlbums.visibility = View.VISIBLE
                            binding.rcvAlbums.adapter = AlbumAdapter(state.albums)
                        }
                    }

                    is AlbumState.Failure -> {
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.GONE
                            binding.rcvAlbums.visibility = View.GONE
                            Toast.makeText(
                                this@SQLiteDemoActivity,
                                state.error.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}