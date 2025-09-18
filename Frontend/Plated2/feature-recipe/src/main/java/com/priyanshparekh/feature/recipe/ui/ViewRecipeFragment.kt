package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.google.android.material.tabs.TabLayoutMediator
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.model.recipe.ViewRecipeResponse
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.resources.R
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.core.utils.SharedPrefManager
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.adapter.ReviewViewPagerAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentViewRecipeBinding

class ViewRecipeFragment : Fragment() {

    private lateinit var binding: FragmentViewRecipeBinding

    private val recipeViewModel: RecipeViewModel by viewModels()

    private lateinit var recipe: ViewRecipeResponse

    private var isRecipeSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = arguments?.getLong(Constants.Args.KEY_RECIPE_ID) ?: -1L
        Log.d("TAG", "onCreate: $recipeId")

        val ingredients = ArrayList<RecipeIngredientDto>()
        val instructions = ArrayList<StepDto>()

        val viewPagerAdapter = ReviewViewPagerAdapter(requireContext(), ingredients, instructions)

        recipeViewModel.getRecipe(recipeId)

        recipeViewModel.viewRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "viewRecipeFragment: onViewCreated: viewRecipeStatus: error: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    recipe = status.data
                    val userId = SharedPrefManager.getUserId()

                    recipeViewModel.savedRecipeExists(userId, recipe.recipeId)

                    binding.recipeName.text = recipe.name
                    binding.username.text = recipe.userName
                    binding.tvCategory.text = recipe.category
                    binding.tvCuisine.text = recipe.cuisine

                    var cookingTime = recipe.cookingTime
                    binding.tvCookingTime.text = if (cookingTime < 60) {
                        "%.0f mins".format(cookingTime)
                    } else {
                        cookingTime = cookingTime / 60
                        "%.0f hrs".format(cookingTime)
                    }

                    val ingredientsJson = recipe.ingredients
                    Log.d("TAG", "onCreate: ingredients size: ${ingredientsJson.size}")

                    val instructionsJson = recipe.steps
                    Log.d("TAG", "onCreate: instructions size: ${instructionsJson.size}")

                    ingredients.apply {
                        clear()
                        addAll(ingredientsJson)
                    }

                    instructions.apply {
                        clear()
                        addAll(instructionsJson)
                    }

                    viewPagerAdapter.refreshData()
                }
            }
        }

        recipeViewModel.savedRecipeExistsStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "viewRecipeFragment: onViewCreated: savedRecipeExistsStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    isRecipeSaved = status.data
                    if (isRecipeSaved) {
                        binding.btnSave.setImageResource(R.drawable.round_bookmark_filled_24)
                    } else {
                        binding.btnSave.setImageResource(R.drawable.round_bookmark_border_24)
                    }
                }
            }
        }

        Log.d("TAG", "onCreate: adapter set")

        binding.recipePages.adapter = viewPagerAdapter
        binding.recipePages.orientation = ORIENTATION_HORIZONTAL
        binding.recipePages.currentItem = 0

        val tabTitles = arrayOf("Ingredients", "Procedure")
        TabLayoutMediator(binding.recipeTabs, binding.recipePages) {
                tab, position ->

            tab.text = tabTitles[position]
        }.attach()

        binding.btnSave.setOnClickListener {
            val userId = SharedPrefManager.getUserId()
            if (isRecipeSaved) {
                recipeViewModel.unsaveRecipe(userId, recipeId)
            } else {
                recipeViewModel.saveRecipe(userId, recipeId)
            }
        }


        recipeViewModel.saveRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: saveRecipeStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    binding.btnSave.setImageResource(R.drawable.round_bookmark_filled_24)
                    Log.d("TAG", "onViewCreated: saveRecipeStatus: ${status.data}")
                }
            }
        }

        recipeViewModel.unsaveRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: unsaveRecipeStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    binding.btnSave.setImageResource(R.drawable.round_bookmark_border_24)
                    Log.d("TAG", "onViewCreated: unsaveRecipeStatus: ${status.data}")
                }
            }
        }

//        binding.reviewCount.setOnClickListener {
//            val intent = Intent(this, ReviewActivity::class.java)
//            intent.putExtra(Constants.KEY_RECIPE_ID, recipeId)
//            startActivity(intent)
//        }

//        binding.btnFollow.setOnClickListener {
//            binding.btnFollow.text = "Following"

//            val followerUserId = getSharedPreferences(Constants.LOGIN_PREFS, MODE_PRIVATE).getInt(Constants.KEY_USER_ID, -1)
//            val followedUserId = recipe.userId

//            recipeViewModel.followUser(followerUserId, Follow(followerUserId, followedUserId))
//        }
    }
}