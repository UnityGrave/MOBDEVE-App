package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.FragmentProfileBinding
import com.mobdeve.senateelectioninfo.splash.SplashActivity
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val accountService = AccountServiceImpl.getInstance()

    private lateinit var logout: Button
    private lateinit var editProfile: ImageButton
    private lateinit var profileName: TextView
    private lateinit var profilePosition: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileBirthday: TextView
    private lateinit var profileContactNumber: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        logout = binding.btnLogout
        editProfile = binding.editProfileBtn
        profileName = binding.profileName
        profilePosition = binding.profilePosition // Change this to a dynamic field if needed
        profileEmail = binding.profileEmail // Use the correct ID for email
        profileBirthday = binding.profileBirthday // Add ID for birthday if missing
        profileContactNumber = binding.profileContactNumber // Add ID for contact number if missing

        if (accountService.hasUser()) {
            viewLifecycleOwner.lifecycleScope.launch {
                val userProfile = accountService.getProfile()!!

                profileName.text = userProfile.firstName + " " + userProfile.lastName
                profileEmail.text = userProfile.email
                profileBirthday.text = userProfile.birthday
                profileContactNumber.text = userProfile.contactNumber
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
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}