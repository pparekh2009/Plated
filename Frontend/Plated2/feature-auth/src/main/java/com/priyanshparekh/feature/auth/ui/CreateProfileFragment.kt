package com.priyanshparekh.feature.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.priyanshparekh.core.model.auth.UserSignUpRequest
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.feature.auth.AuthViewModel
import com.priyanshparekh.feature.auth.databinding.FragmentCreateProfileBinding
import kotlin.getValue

class CreateProfileFragment : Fragment() {

    private lateinit var binding: FragmentCreateProfileBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCreateProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user = if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("user", UserSignUpRequest::class.java)
        } else {
            arguments?.getSerializable("user")
        } as UserSignUpRequest

        binding.btnSkip.setOnClickListener {
            authViewModel.signUp(user)
        }

        binding.btnSetProfile.setOnClickListener {
            val displayName = binding.inputDisplayName.text.toString()
            val bio = binding.bioInput.text.toString()
            val profession = binding.inputProfession.text.toString()
            val website = binding.websiteInput.text.toString()

            user.displayName = displayName
            user.bio = bio
            user.profession = profession
            user.website = website

            if (website.trim().isNotEmpty()) {
                authViewModel.signUp(user)
            }
        }

        authViewModel.signUpStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
                is Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(requireContext(), "Sign Up success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}