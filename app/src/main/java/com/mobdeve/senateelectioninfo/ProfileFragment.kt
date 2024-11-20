package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.splash.SplashActivity
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var logout: Button
    private lateinit var editProfile: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the buttons
        logout = view.findViewById(R.id.btn_logout)
        editProfile = view.findViewById(R.id.editProfileBtn)

        // Set the onClickListener to navigate to LoginFragment
        logout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                AccountServiceImpl().signOut()
                val intent = Intent(activity, SplashActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        editProfile.setOnClickListener {
            replaceFragment(EditProfileFragment())
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