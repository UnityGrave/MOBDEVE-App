package com.mobdeve.senateelectioninfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mobdeve.senateelectioninfo.auth.model.User
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var saveButton: MaterialButton
    private lateinit var inputFirstName: TextInputEditText
    private lateinit var inputLastName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputBirthday: TextInputEditText
    private lateinit var inputContactNumber: TextInputEditText

    private val accountService = AccountServiceImpl.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Initialize all views
        saveButton = view.findViewById(R.id.saveButton)
        inputFirstName = view.findViewById(R.id.inputFirstName)
        inputLastName = view.findViewById(R.id.inputLastName)
        inputEmail = view.findViewById(R.id.inputEmail)
        inputBirthday = view.findViewById(R.id.inputBirthday)
        inputContactNumber = view.findViewById(R.id.inputContactNumber)

        // Load current user data into fields
        loadUserProfile()

        saveButton.setOnClickListener {
            val firstName = inputFirstName.text.toString().trim()
            val lastName = inputLastName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val birthday = inputBirthday.text.toString().trim()
            val contactNumber = inputContactNumber.text.toString().trim()

            if (validateInputs(firstName, lastName, email, birthday, contactNumber)) {
                saveProfile(firstName, lastName, email, contactNumber, birthday)
                replaceFragment(ProfileFragment())
            }
        }

        return view
    }

    private fun loadUserProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userProfile = accountService.getProfile()
                withContext(Dispatchers.Main) {
                    userProfile?.let {
                        inputFirstName.setText(it.firstName)
                        inputLastName.setText(it.lastName)
                        inputEmail.setText(it.email)
                        inputBirthday.setText(it.birthday)
                        inputContactNumber.setText(it.contactNumber)
                    } ?: run {
                        Toast.makeText(context, "No profile found", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to load profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInputs(
        firstName: String, lastName: String, email: String,
        birthday: String, contactNumber: String
    ): Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
            birthday.isEmpty() || contactNumber.isEmpty()
        ) {
            Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contactNumber.length != 11 || !contactNumber.all { it.isDigit() }) {
            Toast.makeText(context, "Invalid mobile number", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun saveProfile(
        firstName: String, lastName: String, email: String,
        contactNumber: String, birthday: String
    ) {
        val updatedUser = User(
            id = accountService.currentUserId,
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            contactNumber = contactNumber,
            profilePicture = ""  // or use an existing one
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val success = accountService.updateProfile(updatedUser)
                withContext(Dispatchers.Main) {
                    if (success) {
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}