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

    private lateinit var logout: Button
    private lateinit var editProfile: ImageButton
    private lateinit var profileName: TextView
    private lateinit var profilePosition: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profileBirthday: TextView
    private lateinit var profileContactNumber: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Initialize views
        logout = binding.btnLogout
        editProfile = binding.editProfileBtn
        profileName = binding.profileName
        profilePosition = binding.profilePosition // Change this to a dynamic field if needed
        profileEmail = binding.profileEmail // Use the correct ID for email
        profileBirthday = binding.profileBirthday // Add ID for birthday if missing
        profileContactNumber = binding.profileContactNumber // Add ID for contact number if missing

        // Load user profile from Firestore
        loadUserProfile()

        // Logout functionality
        logout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                AccountServiceImpl().signOut()
                val intent = Intent(activity, SplashActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        // Edit profile functionality
        editProfile.setOnClickListener {
            replaceFragment(EditProfileFragment())
        }

        return view
    }

    private fun loadUserProfile() {
        val db = FirebaseFirestore.getInstance()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email // Ensure user is authenticated

        if (userEmail != null) {
            db.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val user = documents.documents[0].data
                        if (user != null) {
                            // Update UI with user details
                            profileName.text = "${user["firstName"]} ${user["lastName"]}"
                            profilePosition.text = "Position: User" // Adjust if position is part of the profile
                            profileEmail.text = user["email"] as String
                            profileBirthday.text = user["birthday"] as String
                            profileContactNumber.text = user["contactNumber"] as String
                        }
                    } else {
                        Toast.makeText(context, "User profile not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to load profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "User is not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}