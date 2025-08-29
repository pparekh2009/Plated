package com.priyanshparekh.feature.recipe

import com.priyanshparekh.core.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {

    suspend fun loadIngredients() = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.loadIngredients()
    }

    suspend fun getRecipe(recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getRecipe(recipeId)
    }
}