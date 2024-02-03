package com.example.filesystemdemo.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkManager(val context: Context) {
    private var cm: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    var isNetworkAvailable = false

    init {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        configureNetworkCallback()
    }

    private fun configureNetworkCallback() {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapabilities = cm?.getNetworkCapabilities(network)
                isNetworkAvailable =
                    networkCapabilities != null && (networkCapabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    ) && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            }

            override fun onUnavailable() {
                isNetworkAvailable = false
            }
        }
        val request =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        networkCallback?.let {
            cm?.registerNetworkCallback(request, it)
        }
    }

    fun destroyConnectivityManager() {
        networkCallback?.let {
            cm?.unregisterNetworkCallback(it)
        }
    }
}