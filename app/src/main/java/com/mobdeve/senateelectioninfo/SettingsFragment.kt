package com.mobdeve.senateelectioninfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.mobdeve.senateelectioninfo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using the correct binding class
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Initialize views
        setupAppearanceOptions()
        setupNotificationSwitch()

        return binding.root
    }

    private fun setupAppearanceOptions() {
        // Set default selection for Appearance (Auto by default)
        binding.radioAuto.isChecked = true

        // Listener for appearance changes
        binding.radioGroupAppearance.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_auto -> updateAppearanceMode("Auto")
                R.id.radio_day -> updateAppearanceMode("Day")
                R.id.radio_night -> updateAppearanceMode("Night")
            }
        }
    }

    private fun updateAppearanceMode(mode: String) {
        when (mode) {
            "Auto" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            "Day" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Night" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setupNotificationSwitch() {
        // Set default value for notifications (enabled by default)
        binding.switchNotifications.isChecked = true

        // Listener for enabling/disabling notifications
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableNotifications()
            } else {
                disableNotifications()
            }
        }
    }

    private fun enableNotifications() {
        // Logic to enable notifications
    }

    private fun disableNotifications() {
        // Logic to disable notifications
    }
}