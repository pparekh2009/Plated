package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.google.android.material.tabs.TabLayoutMediator
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.feature.recipe.R
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.adapter.ReviewViewPagerAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentViewRecipeBinding
import kotlin.collections.addAll
import kotlin.getValue
import kotlin.text.clear

class ViewRecipeFragment : Fragment() {

    private lateinit var binding: FragmentViewRecipeBinding

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewRecipeBinding.inflate(layoutInflater)

        val recipeId = arguments?.getLong(Constants.Args.KEY_RECIPE_ID) ?: -1L
        recipeViewModel.getRecipePreparationData(recipeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ingredients = ArrayList<RecipeIngredientDto>()
        val steps = ArrayList<StepDto>()

        val viewPagerAdapter = ReviewViewPagerAdapter(requireContext(), ingredients, steps)

        binding.recipePages.adapter = viewPagerAdapter
        binding.recipePages.orientation = ORIENTATION_HORIZONTAL
        binding.recipePages.currentItem = 0

        val tabTitles = arrayOf("Ingredients", "Procedure")
        TabLayoutMediator(binding.recipeTabs, binding.recipePages) {
                tab, position ->

            tab.text = tabTitles[position]
        }.attach()

        binding.btnStartCooking.setOnClickListener {
            NavigatorProvider.commonNavigator.openCookModeFragment(findNavController(), ingredients, steps)
        }

        recipeViewModel.recipePreparationStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: recipePreparationStatus: error: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    val recipePreparationData = status.data

                    val ingredientsJson = recipePreparationData.ingredients
                    Log.d("TAG", "onCreate: ingredients size: ${ingredientsJson.size}")

                    val instructionsJson = recipePreparationData.steps
                    Log.d("TAG", "onCreate: instructions size: ${instructionsJson.size}")

                    ingredients.apply {
                        clear()
                        addAll(ingredientsJson)
                    }

                    steps.apply {
                        clear()
                        addAll(instructionsJson)
                    }

                    viewPagerAdapter.refreshData()
                }
            }
        }
    }
}