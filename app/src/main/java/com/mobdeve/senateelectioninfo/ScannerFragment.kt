package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
        barcodeScannerView?.setStatusText("")

        // Set up the scanner callback
        barcodeScannerView?.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    handleScannedResult(it.text)
                }
            }
        })

        return view
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
}