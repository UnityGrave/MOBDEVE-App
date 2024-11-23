package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.FragmentProfileBinding
import com.mobdeve.senateelectioninfo.splash.SplashActivity
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val accountService = AccountServiceImpl.getInstance()

    private lateinit var logout: Button
    private lateinit var editProfile: Button

    private lateinit var profileName: TextView
    private lateinit var profilePosition: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileBirthday: TextView
    private lateinit var profileContactNumber: TextView

    private lateinit var profileHeaderName: TextView
    private lateinit var profileHeaderEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Initialize views
        logout = binding.btnLogout
        editProfile = binding.btnProfileEdit

        profileName = binding.txtProfileName
        profileEmail = binding.txtProfileEmail // Use the correct ID for email
        profileBirthday = binding.txtProfileBirthday // Add ID for birthday if missing
        profileContactNumber = binding.txtProfileContactNumber // Add ID for contact number if missing

        profileHeaderName = binding.txtProfileHeaderName
        profileHeaderEmail = binding.txtProfileHeaderEmail

        if (accountService.hasUser()) {
            viewLifecycleOwner.lifecycleScope.launch {
                val userProfile = accountService.getProfile()!!

                val name = userProfile.firstName + " " + userProfile.lastName

                profileName.text = name
                profileEmail.text = userProfile.email
                profileBirthday.text = userProfile.birthday
                profileContactNumber.text = userProfile.contactNumber

                profileHeaderName.text = name
                profileHeaderEmail.text = userProfile.email
            }
        }

        // Logout functionality
        logout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                accountService.signOut()
                val intent = Intent(activity, SplashActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        // Edit profile functionality
        editProfile.setOnClickListener {
            replaceFragment(EditProfileFragment())
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}