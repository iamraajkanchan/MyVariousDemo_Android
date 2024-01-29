package com.example.filesystemdemo.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.filesystemdemo.R
import com.example.filesystemdemo.databinding.ActivityWebsocketDemoBinding
import com.example.filesystemdemo.services.ChatListener
import com.example.filesystemdemo.utilities.SOCKET_URL
import com.example.filesystemdemo.utilities.Utility
import com.example.filesystemdemo.viewModels.WebsocketDemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

@AndroidEntryPoint
class WebsocketDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebsocketDemoBinding
    private lateinit var chatListener: ChatListener
    private lateinit var viewModel: WebsocketDemoViewModel

    @Inject
    lateinit var okHttpClient: OkHttpClient
    private var webSocket: WebSocket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebsocketDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this)[WebsocketDemoViewModel::class.java]
        chatListener = ChatListener(viewModel)
        viewModel.socketStatus.observe(this) { status ->
            status?.let {
                binding.tvMessage.text = if (it) "Connected" else "Disconnected"
            }
        }
        var messageString = ""
        viewModel.message.observe(this) {
            messageString = "${if (it.first) "You" else "Other"}: ${it.second}\n"
            binding.tvMessage.text = messageString
        }
        binding.btnConnect.setOnClickListener {
            webSocket = okHttpClient.newWebSocket(createRequest(), chatListener)
        }
        binding.btnDisconnect.setOnClickListener {
            webSocket?.close(1000, "Chat Terminated")
            viewModel.setStatus(false)
        }
        binding.btnSend.setOnClickListener {
            if (binding.edtMessage.text.isNotBlank() && binding.edtMessage.text.isNotEmpty()) {
                webSocket?.send(binding.edtMessage.text.toString())
                viewModel?.setMessage(Pair(true, binding.edtMessage.text.toString()))
            } else {
                Toast.makeText(
                    this@WebsocketDemoActivity,
                    "Please enter some text in the message column!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun createRequest(): Request {
        return Request.Builder().url(SOCKET_URL).build()
    }
}