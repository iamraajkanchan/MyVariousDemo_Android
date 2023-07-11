package com.example.filesystemdemo.services

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.filesystemdemo.R
import com.example.filesystemdemo.utilities.NOTIFICATION_CHANNEL
import com.example.filesystemdemo.utilities.NOTIFICATION_NAME
import com.example.filesystemdemo.utilities.NOTIFICATION_TITLE
import com.example.filesystemdemo.utilities.Utility
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

private const val LOCATION_NOTIFICATION_SERVICE_ID = 4

class LocationNotificationService : Service() {

    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val utility: Utility by lazy { Utility(applicationContext) }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(2))
                .setIntervalMillis(TimeUnit.SECONDS.toMillis(2))
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .setWaitForAccurateLocation(true)
                .build()
        locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(loctionAvailability: LocationAvailability) {
                if (!loctionAvailability.isLocationAvailable) {
                    utility.showSnackBar("No Location Available")
                }
                super.onLocationAvailability(loctionAvailability)
            }

            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.lastLocation != null && locationResult.locations.size > 0) {
                    val lastLocation = locationResult.lastLocation
                    showNotification("${lastLocation?.latitude}", "${lastLocation?.longitude}")
                }
                super.onLocationResult(locationResult)
            }
        }
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return START_NOT_STICKY
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        return START_STICKY
    }

    private fun showNotification(latitude: String, longitude: String) {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
            notificationManager.createNotificationChannel(channel)
            val builder = Notification.Builder(applicationContext, NOTIFICATION_CHANNEL)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Latitude: $latitude, Longitude: $longitude")
                .setStyle(Notification.BigTextStyle().bigText(""))
            builder.build()
        } else {
            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setLargeIcon(utility.vectorToBitmap(R.drawable.ic_launcher_foreground))
                .setSmallIcon(R.drawable.icon_delete)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("${utility.getCurrentTime()} Latitude: $latitude, Longitude: $longitude")
                .setAutoCancel(false)
            builder.build()
        }
        notificationManager.notify(LOCATION_NOTIFICATION_SERVICE_ID, notification)
        startForeground(LOCATION_NOTIFICATION_SERVICE_ID, notification)
    }

    override fun onDestroy() {
        fusedLocationProviderClient.flushLocations()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
}