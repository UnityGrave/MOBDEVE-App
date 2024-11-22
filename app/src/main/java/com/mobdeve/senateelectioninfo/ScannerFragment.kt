package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import android.Manifest

class ScannerFragment : Fragment() {

    private var barcodeScannerView: DecoratedBarcodeView? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission(), ActivityResultCallback { isGranted ->
            if (isGranted) {
                setUpScanner()
            } else {
                Toast.makeText(requireContext(), "Camera permission is required to scan barcodes", Toast.LENGTH_SHORT).show()
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scanner, container, false)

        barcodeScannerView = view.findViewById(R.id.barcode_scanner)
        barcodeScannerView?.setStatusText("")

        if (isCameraPermissionGranted()) {
            setUpScanner()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        return view
    }

    private fun setUpScanner() {
        barcodeScannerView?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    handleScannedResult(it.text)
                }
            }
        })
    }

    private fun handleScannedResult(scannedResult: String) {
        if (scannedResult.startsWith("http://") || scannedResult.startsWith("https://")) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(scannedResult))
            startActivity(browserIntent)
        } else {
            setResult(scannedResult)
        }
    }

    private fun setResult(scannedResult: String) {
        val resultTextView: TextView? = view?.findViewById(R.id.resultTV)
        resultTextView?.text = scannedResult
    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView?.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView?.pause()
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
}
