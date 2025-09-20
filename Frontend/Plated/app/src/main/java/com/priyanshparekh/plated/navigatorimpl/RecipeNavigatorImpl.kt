package com.priyanshparekh.plated.navigatorimpl

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.priyanshparekh.core.navigation.RecipeNavigator
import com.priyanshparekh.plated.R

class RecipeNavigatorImpl: RecipeNavigator {

    override fun openDetailFragment(navController: NavController) {
        navController.navigate(R.id.detailsFragment)
    }

    override fun openIngredientFragment(navController: NavController) {
        navController.navigate(R.id.ingredientsFragment)
    }

    override fun openStepsFragment(navController: NavController) {
        navController.navigate(R.id.stepsFragment)
    }

    override fun openReviewFragment(navController: NavController) {
        navController.navigate(R.id.reviewFragment)
    }

    override fun navigateHome(navController: NavController) {
        navController.navigate(
            R.id.homeFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, false)
                .build()
        )
    }
}