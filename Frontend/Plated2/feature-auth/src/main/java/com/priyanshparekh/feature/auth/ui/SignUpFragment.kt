package com.priyanshparekh.feature.auth.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.model.auth.UserSignUpRequest
import com.priyanshparekh.feature.auth.R
import com.priyanshparekh.feature.auth.databinding.FragmentSignUpBinding
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignUpBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val user = getUser()

            if (user != null) {

                val bundle = bundleOf("user" to user)

                findNavController().navigate(
                    R.id.createProfileFragment,
                    bundle
                )
            }
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.signUpFragment, true).build()
            )
        }
    }

    private fun getUser(): UserSignUpRequest? {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmPassword.text.toString()

        if (email.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show()
            return null
        }

        val emailPattern = Patterns.EMAIL_ADDRESS.pattern()
        if (!Pattern.matches(emailPattern, email)) {
            Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show()
            return null
        }

        if (password.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
            return null
        }

        if (password.length < 8 || password.length > 20) {
            Toast.makeText(requireContext(), "Password must be between 8 and 20 characters", Toast.LENGTH_SHORT).show()
            return null
        }

        if (confirmPassword.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Confirm Password cannot be empty", Toast.LENGTH_SHORT).show()
            return null
        }

        if (password != confirmPassword) {
            Log.d("TAG", "getUser: Password: $password, Confirm Password: $confirmPassword")
            Toast.makeText(requireContext(), "Confirm password and password does not match", Toast.LENGTH_SHORT).show()
            return null
        }

        val user = UserSignUpRequest(
            email = email,
            password = password
        )

        return user
    }
}