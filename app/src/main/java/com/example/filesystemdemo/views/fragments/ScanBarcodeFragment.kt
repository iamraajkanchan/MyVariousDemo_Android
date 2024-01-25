package com.example.filesystemdemo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.filesystemdemo.databinding.FragmentScanBarcodeBinding
import com.example.filesystemdemo.views.interfaces.IScanBarcode
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

/**
 * A simple [Fragment] subclass.
 */
class ScanBarcodeFragment(private val iScanBarcode: IScanBarcode) : Fragment() {
    private lateinit var binding: FragmentScanBarcodeBinding

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            iScanBarcode.getBarcodeValue(result.contents)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentScanBarcodeBinding.inflate(inflater, container, false)
        barcodeLauncher.launch(ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
            setCameraId(0)
            setBeepEnabled(true)
            setOrientationLocked(true)
            setBarcodeImageEnabled(true)
        })
        return binding.root
    }
}