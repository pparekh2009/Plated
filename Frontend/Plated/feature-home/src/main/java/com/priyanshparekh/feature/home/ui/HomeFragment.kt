package com.priyanshparekh.feature.home.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.feature.home.HomeViewModel
import com.priyanshparekh.feature.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        homeViewModel.getTrendingRecipes()
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

        homeViewModel.trendingRecipesStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: trendingRecipesStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    Log.d("TAG", "onViewCreated: trendingRecipesStatus: ${status.data.size}")
                }
            }
        }
    }
}