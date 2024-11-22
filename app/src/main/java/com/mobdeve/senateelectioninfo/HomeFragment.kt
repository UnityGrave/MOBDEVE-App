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
import com.mobdeve.senateelectioninfo.databinding.FragmentHomeBinding

import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var nickName: TextView

    private val accountService = AccountServiceImpl.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        nickName = binding.txtHomeHeaderName

        viewLifecycleOwner.lifecycleScope.launch {
            val userProfile = accountService.getProfile()!!
            val name = "Hi, " + userProfile.firstName + "!"
            nickName.text = name

        }

        // Inflate the layout for this fragment
        return binding.root
    }
}