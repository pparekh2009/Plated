package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.adapter.ReviewViewPagerAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentReviewBinding
import kotlin.getValue

class ReviewFragment : Fragment() {

    private lateinit var binding: FragmentReviewBinding

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentReviewBinding.inflate(layoutInflater)
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
        val instructions = ArrayList<StepDto>()

        val reviewViewPagerAdapter = ReviewViewPagerAdapter(requireContext(), ingredients, instructions)

        binding.recipePages.adapter = reviewViewPagerAdapter
        binding.recipePages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.recipePages.currentItem = 0

        val tabTitles = arrayOf("Ingredients", "Steps")
        TabLayoutMediator(binding.recipeTabs, binding.recipePages) {
                tab, position ->

            tab.text = tabTitles[position]
        }.attach()

        recipeViewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            if (recipe == null) {
                Log.d("TAG", "onViewCreated: recipe null")
                return@observe
            }

            binding.recipeName.text = recipe.recipeName
            binding.description.text = recipe.description
            binding.servings.text = "${recipe.servingSize}"
            var time = recipe.cookingTime
            if (time > 60) {
                time /= 60
                binding.time.text = "$time hrs"
            } else {
                binding.time.text = "$time mins"
            }

            binding.cuisine.text = recipe.cuisine
            binding.category.text = recipe.category

            ingredients.addAll(recipe.ingredientList)
            instructions.addAll(recipe.stepList)

            reviewViewPagerAdapter.refreshData()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnPost.setOnClickListener {
            Log.d("TAG", "onViewCreated: ingredients: ${recipeViewModel.recipe.value?.ingredientList}")
            Log.d("TAG", "onViewCreated: steps: ${recipeViewModel.recipe.value?.stepList}")

            recipeViewModel.recipe.value?.let {
                recipeViewModel.postRecipe(it)
            }
        }

        recipeViewModel.postRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
                is Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                is Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    recipeViewModel.resetRecipe()

                    Toast.makeText(requireContext(), "Recipe Posted", Toast.LENGTH_SHORT).show()

                    NavigatorProvider.recipeNavigator.navigateHome(findNavController())
                }
            }
        }
    }
}