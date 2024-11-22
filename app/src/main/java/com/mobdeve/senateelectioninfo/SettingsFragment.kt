package com.mobdeve.senateelectioninfo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.Manifest
import androidx.core.app.NotificationManagerCompat


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val accountService = AccountServiceImpl.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using the correct binding class
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Initialize views
        setupAppearanceOptions()
        setupNotificationSwitch()
        loadUserProfile()

        return binding.root
    }

    private fun loadUserProfile() {
        // Load the user profile and set the state of the 2FA switch based on the authenticator field
        CoroutineScope(Dispatchers.IO).launch {
            val userProfile = accountService.getProfile()
            withContext(Dispatchers.Main) {
                userProfile?.let {
                    // Set the switch state based on the "authenticator" field
                    binding.switch2FA.isChecked = it.authenticator == "enabled"
                    setupSecurityOptions()
                }
            }
        }
    }

    private fun setupSecurityOptions() {
        binding.switch2FA.setOnCheckedChangeListener { _, isChecked ->
            val newStatus = if (isChecked) "enabled" else "disabled"
            updateTwoFactorAuthStatus(newStatus)
        }
    }

    private fun updateTwoFactorAuthStatus(newStatus: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val updateResult = accountService.updateTwoFactorAuth(newStatus)
        }
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
                R.id.radio_auto -> {
                    updateAppearanceMode("Auto")
                    showSampleNotification()
                }
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
        val areNotificationsEnabled = NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()
        binding.switchNotifications.isChecked = areNotificationsEnabled

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request the permission
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                    )
                } else {
                    // Permission already granted or not needed
                    enableNotifications(requireContext())
                }
            } else {
                // User disabled notifications
                disableNotifications(requireContext())
            }
        }
    }

    private fun showSampleNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "69"
            val channelName = "notif_channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create notification channel
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)

            val notification = android.app.Notification.Builder(requireContext(), channelId)
                .setContentTitle("Auto Mode Enabled")
                .setContentText("You have enabled Auto appearance mode.")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setAutoCancel(true)
                .build()

            // Show the notification
            notificationManager.notify(1, notification)
        }
    }

    private fun enableNotifications(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "69"
            val channelName = "notif_channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun disableNotifications(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "69"

            val channel = NotificationChannel(channelId, "notif_channel", NotificationManager.IMPORTANCE_NONE)
            notificationManager.createNotificationChannel(channel)

            notificationManager.cancelAll()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable notifications
                enableNotifications(requireContext())
                binding.switchNotifications.isChecked = true
            } else {
                // Permission denied, update UI to reflect this
                binding.switchNotifications.isChecked = false
            }
        }
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }
}