package com.mobdeve.senateelectioninfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScannerFragment : Fragment() {

    private var barcodeScannerView: DecoratedBarcodeView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scanner, container, false)

        // Initialize the barcode scanner view
        barcodeScannerView = view.findViewById(R.id.barcode_scanner)

        // Set up the scanner callback
        barcodeScannerView?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    setResult(it.text)
                }
            }

            override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>) {
                // Handle possible result points if needed
            }
        })

        return view
    }

    private fun setResult(scannedResult: String) {
        val resultTextView: TextView? = view?.findViewById(R.id.resultTV)
        resultTextView?.text = scannedResult
    }

    override fun onResume() {
        super.onResume()
        // Start the scanner when the fragment is visible
        barcodeScannerView?.resume()
    }

    override fun onPause() {
        super.onPause()
        // Pause the scanner when the fragment is not visible
        barcodeScannerView?.pause()
    }
}