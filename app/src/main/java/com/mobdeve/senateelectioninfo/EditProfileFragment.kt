package com.mobdeve.senateelectioninfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var saveButton: MaterialButton
    private lateinit var inputFirstName: TextInputEditText
    private lateinit var inputLastName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputBirthday: TextInputEditText
    private lateinit var inputContactNumber: TextInputEditText

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
            val firstName = inputFirstName.text.toString().trim()
            val lastName = inputLastName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val birthday = inputBirthday.text.toString().trim()
            val contactNumber = inputContactNumber.text.toString().trim()

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
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val documentId = documents.documents[0].id
                    db.collection("users").document(documentId)
                        .update(user)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to update: ${e.message}", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                } else {
                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Successfully saved", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to query: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }
}