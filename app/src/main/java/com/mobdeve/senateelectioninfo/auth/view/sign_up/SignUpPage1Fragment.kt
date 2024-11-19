package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.databinding.FragmentSignupPage1Binding

class SignUpPage1Fragment: Fragment() {

    lateinit var binding: FragmentSignupPage1Binding

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupPage1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSignUpFirstName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateFirstName(text.toString())
            binding.inputSignUpFirstNameLayout.error = ""
        }

        binding.inputSignUpLastName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateLastName(text.toString())
            binding.inputSignUpLastNameLayout.error = ""
        }
    }

    fun setErrorMessages(firstNameError: String?, lastNameError: String?) {
        binding.inputSignUpFirstNameLayout.error = firstNameError
        binding.inputSignUpLastNameLayout.error = lastNameError
    }
}