package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.model.recipe.RecipeDetailResponse
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.resources.R
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.core.utils.SharedPrefManager
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding

    private val recipeViewModel: RecipeViewModel by viewModels()

    private lateinit var recipe: RecipeDetailResponse

    private var isRecipeSaved = false
    private var isRecipeLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater)
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

        binding.btnViewRecipe.setOnClickListener {
            NavigatorProvider.commonNavigator.openViewRecipeFragment(findNavController(), recipeId)
        }

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
                    recipeViewModel.likedRecipeExists(userId, recipe.recipeId)

                    binding.recipeName.text = recipe.name
                    binding.username.text = "By ${recipe.userName}"
                    binding.tvCategory.text = "Category: ${recipe.category}"
                    binding.tvCuisine.text = "Cuisine: ${recipe.cuisine}"
                    binding.tvServings.text = "Servings: ${recipe.servingSize}"

                    var cookingTime = recipe.cookingTime
                    binding.tvCookingTime.text = if (cookingTime < 60) {
                        "Cooking Time: %.0f mins".format(cookingTime)
                    } else {
                        cookingTime = cookingTime / 60
                        "Cooking Time: %.0f hrs".format(cookingTime)
                    }
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


        recipeViewModel.likedRecipeExistsStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "viewRecipeFragment: onViewCreated: likedRecipeExistsStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    isRecipeLiked = status.data
                    if (isRecipeLiked) {
                        binding.btnLike.setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        binding.btnLike.setImageResource(R.drawable.outline_favorite_24)
                    }
                }
            }
        }


        binding.btnLike.setOnClickListener {
            val userId = SharedPrefManager.getUserId()
            if (isRecipeLiked) {
                recipeViewModel.unlikeRecipe(userId, recipeId)
            } else {
                recipeViewModel.likeRecipe(userId, recipeId)
            }
        }

        recipeViewModel.likeRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: likeRecipeStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    binding.btnLike.setImageResource(R.drawable.baseline_favorite_24)
                    Log.d("TAG", "onViewCreated: likeRecipeStatus: ${status.data}")
                }
            }
        }

        recipeViewModel.unlikeRecipeStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: unlikeRecipeStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    binding.btnLike.setImageResource(R.drawable.outline_favorite_24)
                    Log.d("TAG", "onViewCreated: unlikeRecipeStatus: ${status.data}")
                }
            }
        }
    }
}