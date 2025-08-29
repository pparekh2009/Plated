package com.priyanshparekh.feature.home.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.feature.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get user name
        val username = requireContext().getSharedPreferences(Constants.SharedPrefs.USER_DETAILS_PREF, Context.MODE_PRIVATE).getString(Constants.SharedPrefs.KEY_DISPLAY_NAME, "")
        binding.homeHeader.text = "Hello $username"

        binding.searchBar.setOnClickListener {
            NavigatorProvider.commonNavigator.openSearchFragment(findNavController())
        }
    }
}