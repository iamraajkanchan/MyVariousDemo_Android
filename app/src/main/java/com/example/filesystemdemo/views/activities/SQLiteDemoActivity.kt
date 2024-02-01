package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.filesystemdemo.R
import com.example.filesystemdemo.databinding.ActivitySqliteDemoBinding
import com.example.filesystemdemo.repository.AlbumState
import com.example.filesystemdemo.viewModels.SQLiteDemoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SQLiteDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqliteDemoBinding
    private val viewModel by viewModels<SQLiteDemoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySqliteDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAlbums()
    }

    private fun getAlbums() {
        lifecycleScope.launch {
            viewModel.albumState.collectLatest { state ->
                when (state) {
                    is AlbumState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rcvAlbums.visibility = View.GONE
                    }

                    is AlbumState.Success -> {
                        viewModel.setAlbums(state.albums)
                        delay(4000)
                        binding.progressBar.visibility = View.GONE
                        binding.rcvAlbums.visibility = View.VISIBLE
                        for (album in viewModel.getAlbums()) {
                            println("SQLiteDemoActivity :: title : ${album.title}")
                        }
                    }

                    is AlbumState.Failure -> {
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