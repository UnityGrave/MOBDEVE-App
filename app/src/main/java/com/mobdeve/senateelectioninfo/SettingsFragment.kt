package com.mobdeve.senateelectioninfo

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.mobdeve.senateelectioninfo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using the correct binding class
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        setupAppearanceOptions()
        setupNotificationSwitch()
        setupSecurityOptions()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Prevent the activity from restarting when changing night mode
    }


    private fun setupSecurityOptions() {
        // Set default selection for Security (Disabled by default)
        binding.switch2FA.isChecked = false

        // Listener for enabling/disabling 2FA
        binding.switch2FA.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enable2FA()
            }
        }
    }

    private fun enable2FA() {
        // logic for enabling two factor authentication
    }

    private fun setupAppearanceOptions() {
        // Get the current night mode setting
        val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        // Set the default selection based on the current mode
        when (currentMode) {
            Configuration.UI_MODE_NIGHT_YES -> binding.radioNight.isChecked = true
            Configuration.UI_MODE_NIGHT_NO -> binding.radioDay.isChecked = true
            Configuration.UI_MODE_NIGHT_UNDEFINED -> binding.radioAuto.isChecked = true
        }

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
        Handler(Looper.getMainLooper()).post {
            when (mode) {
                "Auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                "Day" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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