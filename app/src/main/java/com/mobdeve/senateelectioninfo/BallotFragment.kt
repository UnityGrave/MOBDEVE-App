package com.mobdeve.senateelectioninfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import com.mobdeve.senateelectioninfo.auth.model.service.impl.SenatorServiceImpl
import com.mobdeve.senateelectioninfo.databinding.FragmentBallotBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BallotFragment : Fragment() {

    private lateinit var binding: FragmentBallotBinding
    private val senatorService = SenatorServiceImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBallotBinding.inflate(inflater, container, false)

        // Fetch senators and populate spinners
        fetchAndPopulateSenators()

        return binding.root
    }

    private fun fetchAndPopulateSenators() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Fetch the list of senators and party-lists from Firestore
                val senators = senatorService.getAllSenators()
                val senatorNames = senators.map { it.name }

                val partyLists = senatorService.getAllPartyLists()

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    // Populate senator spinners
                    populateSpinner(binding.dropdownSenator1, senatorNames)
                    populateSpinner(binding.dropdownSenator2, senatorNames)
                    populateSpinner(binding.dropdownSenator3, senatorNames)
                    populateSpinner(binding.dropdownSenator4, senatorNames)
                    populateSpinner(binding.dropdownSenator5, senatorNames)
                    populateSpinner(binding.dropdownSenator6, senatorNames)
                    populateSpinner(binding.dropdownSenator7, senatorNames)
                    populateSpinner(binding.dropdownSenator8, senatorNames)
                    populateSpinner(binding.dropdownSenator9, senatorNames)
                    populateSpinner(binding.dropdownSenator10, senatorNames)
                    populateSpinner(binding.dropdownSenator11, senatorNames)
                    populateSpinner(binding.dropdownSenator12, senatorNames)

                    // Populate party-list spinner
                    populateSpinner(binding.dropdownPartylist, partyLists)
                }
            } catch (e: Exception) {
                // Handle errors
                e.printStackTrace()
            }
        }
    }

    private fun populateSpinner(spinner: Spinner, data: List<String>) {
        val spinnerData = listOf("None selected") + data

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            spinnerData
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}