package com.priyanshparekh.core.navigation

import android.content.Context
import androidx.navigation.NavController
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto

interface CommonNavigator {

    fun openLoginFragment(context: Context)

    fun openHomeActivity(context: Context)

    fun openSearchFragment(navController: NavController)

    fun openProfileFragment(navController: NavController, userId: Long, isMyProfile: Boolean)

    fun openRecipeDetailFragment(navController: NavController, recipeId: Long)

    fun openViewRecipeFragment(navController: NavController, recipeId: Long)

    fun openCookModeFragment(navController: NavController, ingredients: List<RecipeIngredientDto>, steps: List<StepDto>)
}