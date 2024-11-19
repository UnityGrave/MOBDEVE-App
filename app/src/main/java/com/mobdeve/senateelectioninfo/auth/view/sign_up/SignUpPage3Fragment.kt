package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.databinding.FragmentSignupPage3Binding

class SignUpPage3Fragment: Fragment() {

    lateinit var binding: FragmentSignupPage3Binding

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
        binding = FragmentSignupPage3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSignUpEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.updateEmail(text.toString())
            binding.inputSignUpEmailLayout.error = ""
        }

        binding.inputSignUpPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePassword(text.toString())
            binding.inputSignUpPasswordLayout.error = ""
        }
    }

    fun setErrorMessages(emailError: String?, passwordError: String?) {
        binding.inputSignUpEmail.error = emailError
        binding.inputSignUpPassword.error = passwordError
    }
}