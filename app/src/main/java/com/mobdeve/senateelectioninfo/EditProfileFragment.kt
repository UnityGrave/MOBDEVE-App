package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.splash.SplashActivity
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var saveButton: Button
    private lateinit var inputFirstName: EditText
    private lateinit var inputLastName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputBirthday: EditText
    private lateinit var inputContactNumber: EditText

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

        saveButton.setOnClickListener {
            val firstName = inputFirstName.text.toString()
            val lastName = inputLastName.text.toString()
            val email = inputEmail.text.toString()
            val birthday = inputBirthday.text.toString()
            val contactNumber = inputContactNumber.text.toString()

            if (validateInputs(firstName, lastName, email, birthday, contactNumber)) {
                saveFireStore(firstName, lastName, email, contactNumber, birthday)
                replaceFragment(ProfileFragment())
            }
        }

        return view
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
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun saveFireStore(
        firstName: String, lastName: String, email: String,
        contactNumber: String, birthday: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["firstName"] = firstName
        user["lastName"] = lastName
        user["email"] = email
        user["contactNumber"] = contactNumber
        user["birthday"] = birthday

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->  // Use 'e' or any name for the exception
                Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace() // Logs the detailed error in Logcat
            }
    }
}