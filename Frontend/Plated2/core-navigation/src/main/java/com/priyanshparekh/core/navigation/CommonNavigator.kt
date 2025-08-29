package com.priyanshparekh.core.navigation

import android.content.Context
import androidx.navigation.NavController

interface CommonNavigator {

    fun openLoginFragment(context: Context)

    fun openHomeActivity(context: Context)

    fun openSearchFragment(navController: NavController)

    fun openProfileFragment(navController: NavController, userId: Long, isMyProfile: Boolean)

    fun openViewRecipeFragment(navController: NavController, recipeId: Long)
}