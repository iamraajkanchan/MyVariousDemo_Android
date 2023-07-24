package com.example.filesystemdemo.views.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filesystemdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnFileSystem.setOnClickListener {
            if (storagePermissionGranted()) {
                goToFileSystem()
            } else {
                requestPermission(STORAGE_PERMISSIONS, STORAGE_PERMISSIONS_CODE)
            }
        }
        binding.btnNetworkInfo.setOnClickListener {
            if (locationPermissionGranted()) {
                goToNetworkInfo()
            } else {
                requestPermission(LOCATION_PERMISSIONS, LOCATION_PERMISSIONS_CODE)
            }
        }
        binding.btnViewPagerDemoActivity.setOnClickListener { goToViewPagerDemo() }
        binding.btnViewPagerDemoFragment.setOnClickListener { goToViewPagerDemoWithFragment() }
        binding.btnWorkManagerDemo.setOnClickListener {
            if (storagePermissionGranted()) {
                goToWorkMangerDemo()
            } else {
                requestPermission(STORAGE_PERMISSIONS, WORKER_PERMISSIONS_CODE)
            }
        }
        binding.btnJobSchedulerDemo.setOnClickListener { goToJobSchedulerDemo() }
        binding.btnAlarmManagerDemo.setOnClickListener { goToAlarmManagerDemo() }
        binding.btnBroadcastReceiverDemo.setOnClickListener { goToBroadcastReceiverDemo() }
        binding.btnTimerDemo.setOnClickListener { goToTimerDemo() }
        binding.btnPersistTimerDemo.setOnClickListener { goToPersistTimerDemo() }
        binding.btnConcurrencyDemo.setOnClickListener { goToConcurrencyDemo() }
        binding.btnConnectMPower.setOnClickListener { goToMPowerApplication() }
    }

    private fun storagePermissionGranted() = STORAGE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this@MainActivity, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun locationPermissionGranted() = LOCATION_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this@MainActivity, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permissions: Array<out String>, permissionCode: Int) {
        ActivityCompat.requestPermissions(
            this@MainActivity, permissions, permissionCode
        )
    }

    private fun goToFileSystem() {
        Intent(this@MainActivity, FileSystemActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToNetworkInfo() {
        Intent(this@MainActivity, NetworkInfoActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToViewPagerDemo() {
        Intent(this@MainActivity, ViewPagerDemoActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToViewPagerDemoWithFragment() {
        Intent(this@MainActivity, ViewPagerDemoActivityWithFragment::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToWorkMangerDemo() {
        Intent(this@MainActivity, WorkManagerDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToJobSchedulerDemo() {
        Intent(this@MainActivity, JobSchedulerDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToAlarmManagerDemo() {
        Intent(this@MainActivity, AlarmManagerDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToBroadcastReceiverDemo() {
        Intent(this@MainActivity, BroadcastReceiverDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToTimerDemo() {
        Intent(this@MainActivity, TimerDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToPersistTimerDemo() {
        Intent(this@MainActivity, PersistTimerDemo::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
    }

    private fun goToConcurrencyDemo() {
        Intent(this@MainActivity, ConcurrencyDemoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(this)
        }
    }

    private fun goToMPowerApplication() {
        // Use the below code to connect the launcher activity.
        /*
        val mPowerIntent = packageManager.getLaunchIntentForPackage("com.mobicule.msales.info")
        */
        try {
            Intent().apply {
                component = ComponentName(
                    "com.mobicule.msales.info",
                    "com.mobicule.msales.info.kotlin.AchieversClub.View.AchieversClubCircleListActivity"
                )
                startActivity(this)
            }
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace(System.out)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSIONS_CODE) {
            STORAGE_PERMISSIONS.forEach { s ->
                for (permission in permissions) {
                    if (permission == s) {
                        for (grantResult in grantResults) {
                            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                                goToFileSystem()
                            } else {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                        this@MainActivity, s
                                    )
                                ) {
                                    Snackbar.make(
                                        binding.mainCoordinator,
                                        "Application Need Storage Permission",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
        if (requestCode == LOCATION_PERMISSIONS_CODE) {
            LOCATION_PERMISSIONS.forEach { s ->
                for (permission in permissions) {
                    if (permission == s) {
                        for (grantResult in grantResults) {
                            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                                goToNetworkInfo()
                            } else {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                        this@MainActivity, s
                                    )
                                ) {
                                    Snackbar.make(
                                        binding.mainCoordinator,
                                        "Application Need Location Permission",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
        if (requestCode == WORKER_PERMISSIONS_CODE) {
            STORAGE_PERMISSIONS.forEach { s ->
                for (permission in permissions) {
                    if (permission == s) {
                        for (grantResult in grantResults) {
                            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                                goToWorkMangerDemo()
                            } else {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                        this@MainActivity, s
                                    )
                                ) {
                                    Snackbar.make(
                                        binding.mainCoordinator,
                                        "Application Need Storage Permission",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val LOCATION_PERMISSIONS_CODE = 10
        val LOCATION_PERMISSIONS = mutableListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }
        }.toTypedArray()

        const val STORAGE_PERMISSIONS_CODE = 11
        const val WORKER_PERMISSIONS_CODE = 12
        val STORAGE_PERMISSIONS = mutableListOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

        const val CAMERA_PERMISSIONS_CODE = 12
        val CAMERA_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
    }
}