package com.priyanshparekh.feature.auth.ui

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.model.auth.UserLoginRequest
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.feature.auth.AuthViewModel
import com.priyanshparekh.feature.auth.R
import com.priyanshparekh.feature.auth.databinding.FragmentLoginBinding
import java.util.regex.Pattern
import kotlin.getValue

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            val userLoginRequest = getUserLoginRequest()

            if (userLoginRequest != null) {
                authViewModel.login(userLoginRequest)
            }
        }

        binding.btnSignUp.setOnClickListener {

            findNavController().navigate(
                R.id.signUpFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
            )
        }

        authViewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
                is Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                is Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    NavigatorProvider.commonNavigator.openHomeActivity(requireContext())

                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUserLoginRequest(): UserLoginRequest? {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

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

        return UserLoginRequest(email, password)
    }
}