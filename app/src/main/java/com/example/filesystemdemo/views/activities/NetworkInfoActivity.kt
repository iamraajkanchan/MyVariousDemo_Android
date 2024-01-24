package com.example.filesystemdemo.views.activities

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filesystemdemo.databinding.ActivityNetworkInfoBinding
import com.example.filesystemdemo.views.adapters.NetworkInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class NetworkInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkInfoBinding
    @Inject
    lateinit var locationManager: LocationManager
    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0
    private val locationListener = LocationListener { location ->
        if (location.hasAltitude()) {
            myLatitude = location.latitude
            myLongitude = location.longitude
        }
    }
    private lateinit var telePhonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this, MainActivity.LOCATION_PERMISSIONS[0]
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                MainActivity.LOCATION_PERMISSIONS[1]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 4000, 1f, locationListener
        )
        telePhonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val cellInfoList = telePhonyManager.allCellInfo
        if (cellInfoList.isNotEmpty()) {
            for (cellInfo in cellInfoList) {
                getCellInformation(cellInfo)
            }
        }
    }

    private fun getCellInformation(cellInfo: CellInfo) {
        val cellInfoList: ArrayList<String> = ArrayList()
        if (cellInfo is CellInfoWcdma) {
            val info = StringBuilder("")
            val cellIdentityWcdma = cellInfo.cellIdentity as CellIdentityWcdma
            info.append("Cell Identity: ${cellIdentityWcdma.cid}\n")
            info.append("Location Area Code: ${cellIdentityWcdma.lac}\n")
            info.append("Primary Scrambling Code: ${cellIdentityWcdma.psc}\n")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.append("Mobile Network Operator: ${cellIdentityWcdma.mobileNetworkOperator}\n")
                info.append("Mobile Network Code: ${cellIdentityWcdma.mncString}\n")
                info.append("Mobile Country Code: ${cellIdentityWcdma.mccString}\n")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                for (additionalInfo in cellIdentityWcdma.additionalPlmns) {
                    info.append("PLMN ID: $additionalInfo\n")
                }
                val closedSubscriberGroupInfo = cellIdentityWcdma.closedSubscriberGroupInfo
                info.append("CSG Identity: ${closedSubscriberGroupInfo?.csgIdentity ?: 0}\n")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                info.append("UMTS Absolute RF Channel Number: ${cellIdentityWcdma.uarfcn}\n")
            }
            cellInfoList.add(info.toString())
        }
        if (cellInfo is CellInfoLte) {
            val info = StringBuilder("")
            val cellIdentityLte = cellInfo.cellIdentity as CellIdentityLte
            info.append("Cell Identity: ${cellIdentityLte.ci}\n")
            info.append("Physical Cell Id: ${cellIdentityLte.pci}\n")
            info.append("Tracking Area Code: ${cellIdentityLte.tac}\n")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                info.append("BandWidth: ${cellIdentityLte.bandwidth}\n")
                info.append("Mobile Network Operator: ${cellIdentityLte.mobileNetworkOperator}\n")
                info.append("Mobile Network Code: ${cellIdentityLte.mncString}\n")
                info.append("Mobile Country Code: ${cellIdentityLte.mccString}\n")
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                info.append("Absolute RF Channel Number: ${cellIdentityLte.earfcn}\n")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                for (additionalInfo in cellIdentityLte.additionalPlmns) {
                    info.append("PLMN ID: $additionalInfo\n")
                }
                val closedSubscriberGroupInfo = cellIdentityLte.closedSubscriberGroupInfo
                info.append("CSG Identity: ${closedSubscriberGroupInfo?.csgIdentity ?: 0}\n")
            }
            cellInfoList.add(info.toString())
        }
        if (cellInfo is CellInfoGsm) {
            val info = StringBuilder("")
            val cellIdentityGsm = cellInfo.cellIdentity as CellIdentityGsm
            info.append("Cell Identity: ${cellIdentityGsm.cid}\n")
            info.append("Primary Scrambling Code: ${cellIdentityGsm.psc}\n")
            info.append("Location Area Code: ${cellIdentityGsm.lac}\n")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                info.append("Mobile Network Operator: ${cellIdentityGsm.mobileNetworkOperator}\n")
                info.append("Mobile Network Code: ${cellIdentityGsm.mncString}\n")
                info.append("Mobile Country Code: ${cellIdentityGsm.mccString}\n")
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                info.append("GSM Absolute RF Channel Number: ${cellIdentityGsm.arfcn}\n")
                info.append("Base Station Identity Code: ${cellIdentityGsm.bsic}\n")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                for (additionalInfo in cellIdentityGsm.additionalPlmns) {
                    info.append("PLMN ID: $additionalInfo\n")
                }
            }
            cellInfoList.add(info.toString())
        }
        initRecyclerView(cellInfoList)
    }

    private fun initRecyclerView(cellInfoList: ArrayList<String>) {
        binding.rvNetworkInfoList.layoutManager = LinearLayoutManager(this@NetworkInfoActivity)
        binding.rvNetworkInfoList.adapter = NetworkInfoAdapter(cellInfoList)
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(locationListener)
    }

}