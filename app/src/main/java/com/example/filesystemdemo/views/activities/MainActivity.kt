package com.example.filesystemdemo.views.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filesystemdemo.databinding.ActivityMainBinding
import com.example.filesystemdemo.utilities.Utility
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val utility = Utility(this@MainActivity)
    private val locationPermissionContractForNetworkDemo = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(), activityResultRegistry
    ) { result ->
        for (permission in LOCATION_PERMISSIONS) {
            if (result[permission] == true) {
                goToNetworkInfo()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity, permission
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

    private val storagePermissionContractForFileSystemDemo = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        for (permission in STORAGE_PERMISSIONS) {
            if (result[permission] == true) {
                goToFileSystem()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity, permission
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

    private val storagePermissionContractForWorkManagerDemo = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        for (permission in STORAGE_PERMISSIONS) {
            if (result[permission] == true) {
                goToWorkMangerDemo()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@MainActivity, permission
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

    private val cameraPermissionContract = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        activityResultRegistry
    ) { isGranted ->
        if (isGranted) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.btnFileSystem.setOnClickListener {
            if (storagePermissionGranted()) {
                goToFileSystem()
            } else {
                storagePermissionContractForFileSystemDemo.launch(STORAGE_PERMISSIONS)
            }
        }
        binding.btnNetworkInfo.setOnClickListener {
            if (locationPermissionGranted()) {
                goToNetworkInfo()
            } else {
                locationPermissionContractForNetworkDemo.launch(LOCATION_PERMISSIONS)
            }
        }
        binding.btnViewPagerDemoActivity.setOnClickListener { goToViewPagerDemo() }
        binding.btnViewPagerDemoFragment.setOnClickListener { goToViewPagerDemoWithFragment() }
        binding.btnWorkManagerDemo.setOnClickListener {
            if (storagePermissionGranted()) {
                goToWorkMangerDemo()
            } else {
                storagePermissionContractForWorkManagerDemo.launch(STORAGE_PERMISSIONS)
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

    private fun locationPermissionGranted() = LOCATION_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this@MainActivity, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun storagePermissionGranted() = STORAGE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this@MainActivity,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun goToFileSystem() = utility.goTo(FileSystemActivity::class.java)

    private fun goToNetworkInfo() = utility.goTo(NetworkInfoActivity::class.java)

    private fun goToViewPagerDemo() = utility.goTo(ViewPagerDemoActivity::class.java)

    private fun goToViewPagerDemoWithFragment() =
        utility.goTo(ViewPagerDemoActivityWithFragment::class.java)

    private fun goToWorkMangerDemo() = utility.goTo(WorkManagerDemo::class.java)

    private fun goToJobSchedulerDemo() = utility.goTo(JobSchedulerDemo::class.java)

    private fun goToAlarmManagerDemo() = utility.goTo(AlarmManagerDemo::class.java)

    private fun goToBroadcastReceiverDemo() = utility.goTo(BroadcastReceiverDemo::class.java)

    private fun goToTimerDemo() = utility.goTo(TimerDemo::class.java)

    private fun goToPersistTimerDemo() = utility.goTo(PersistTimerDemo::class.java)

    private fun goToConcurrencyDemo() = utility.goTo(ConcurrencyDemoActivity::class.java)

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

    companion object {
        val LOCATION_PERMISSIONS = mutableListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }.toTypedArray()

        val STORAGE_PERMISSIONS =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE).apply {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }
                }.toTypedArray()
            } else {
                arrayOf(
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            }


        val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}