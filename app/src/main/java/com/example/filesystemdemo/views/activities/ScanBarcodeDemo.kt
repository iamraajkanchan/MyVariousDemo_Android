package com.example.filesystemdemo.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.util.isNotEmpty
import com.example.filesystemdemo.databinding.ActivityScanBarcodeDemoBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException


class ScanBarcodeDemo : AppCompatActivity() {

    private lateinit var binding: ActivityScanBarcodeDemoBinding
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarcodeDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configure()
    }

    private fun configure() {
        barcodeDetector = BarcodeDetector.Builder(this@ScanBarcodeDemo)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes != null && barcodes.isNotEmpty()) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        for (i in 0 until barcodes.size()) {
                            if (barcodes.get(i) != null) {
                                println(
                                    "ScanBarcodeDemo :: receiveDetections :: barcode : ${
                                        barcodes.get(i).rawValue
                                    }"
                                )
                            }
                        }
                    }
                }
            }
        })

        cameraSource = CameraSource.Builder(this@ScanBarcodeDemo, barcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .setAutoFocusEnabled(true)
            .build()

        binding.svScanBarcode.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                try {
                    if (!cameraPermissionGranted()) {
                        return
                    } else {
                        cameraSource.start(surfaceHolder)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                surfaceHolder.surface.release()
            }
        })

        binding.btnScanBarcode.setOnClickListener {

        }
    }

    private fun cameraPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this@ScanBarcodeDemo,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        barcodeDetector.release()
        cameraSource.release()
        super.onDestroy()
    }
}