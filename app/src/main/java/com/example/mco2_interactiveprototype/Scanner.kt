package com.example.mco2_interactiveprototype

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class Scanner : Fragment() {
    
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()  // Show camera if permission is granted
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val scanLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                setResult(result.contents)
            }
        }

    private fun setResult(scannedResult: String) {
        val resultTextView: TextView? = view?.findViewById(R.id.resultTV)
        resultTextView?.text = scannedResult
    }

    // Show the camera and start scanning QR codes
    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    // Check camera permission and handle the permission flow
    private fun checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()  // Permission already granted, show camera
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(requireContext(), "Camera permission is required to scan QR codes", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)  // Request permission
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scanner, container, false)
        val scanButton: Button = view.findViewById(R.id.scannerBtn)
        scanButton.setOnClickListener {
            checkPermissionCamera()
        }

        return view
    }
}