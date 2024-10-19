package com.example.mco2_interactiveprototype

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class Profile : Fragment(R.layout.fragment_profile) {
    private lateinit var logout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the logout button
        logout = view.findViewById(R.id.btn_logout)

        // Set the onClickListener to navigate to LoginFragment
        logout.setOnClickListener {
            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}