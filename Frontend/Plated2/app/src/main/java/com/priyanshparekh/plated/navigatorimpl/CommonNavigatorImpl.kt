package com.priyanshparekh.plated.navigatorimpl

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.priyanshparekh.core.navigation.CommonNavigator
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.feature.auth.ui.AuthActivity
import com.priyanshparekh.plated.HomeActivity
import com.priyanshparekh.plated.R

class CommonNavigatorImpl: CommonNavigator {

    override fun openLoginFragment(context: Context) {
        val intent = Intent(context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openSearchFragment(navController: NavController) {
        navController.navigate(R.id.searchFragment)
    }

    override fun openProfileFragment(
        navController: NavController,
        userId: Long,
        isMyProfile: Boolean
    ) {

        val bundle = bundleOf(
            Constants.Args.KEY_USER_ID to userId,
            Constants.Args.KEY_IS_MY_PROFILE to isMyProfile
        )

        navController.navigate(
            R.id.profileFragment,
            bundle,
            NavOptions.Builder()
                .setPopUpTo(R.id.profileFragment, false)
                .build()
        )
    }

    override fun openViewRecipeFragment(
        navController: NavController,
        recipeId: Long
    ) {
        val bundle = bundleOf(Constants.Args.KEY_RECIPE_ID to recipeId)

        navController.navigate(
            R.id.viewRecipeFragment,
            bundle
        )
    }
}