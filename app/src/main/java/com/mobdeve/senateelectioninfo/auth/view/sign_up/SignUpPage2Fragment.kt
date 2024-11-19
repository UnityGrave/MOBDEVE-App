package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.databinding.FragmentSignupPage2Binding

class SignUpPage2Fragment: Fragment() {

    lateinit var binding: FragmentSignupPage2Binding

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
        binding = FragmentSignupPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSignUpBirthday.doOnTextChanged { text, _, _, _ ->
            viewModel.updateBirthday(text.toString())
            binding.inputSignUpBirthdayLayout.error = ""
        }

        binding.inputSignUpContactNumber.doOnTextChanged { text, _, _, _ ->
            viewModel.updateContactNumber(text.toString())
            binding.inputSignUpContactNumberLayout.error = ""
        }
    }

    fun setErrorMessages(birthdayError: String?, contactNumberError: String?) {
        binding.inputSignUpBirthdayLayout.error = birthdayError
        binding.inputSignUpContactNumberLayout.error = contactNumberError
    }
}