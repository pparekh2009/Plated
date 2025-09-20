package com.priyanshparekh.core.navigation

import androidx.navigation.NavController

interface RecipeNavigator {

    fun openDetailFragment(navController: NavController)

    fun openIngredientFragment(navController: NavController)

    fun openStepsFragment(navController: NavController)

    fun openReviewFragment(navController: NavController)

    fun navigateHome(navController: NavController)

}