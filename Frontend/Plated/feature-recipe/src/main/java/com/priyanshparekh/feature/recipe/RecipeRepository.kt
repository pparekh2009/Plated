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

    suspend fun getRecipePreparationData(recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getRecipePreparationData(recipeId)
    }

    suspend fun getSavedRecipes(userId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getSavedRecipes(userId)
    }

    suspend fun checkSavedRecipeExists(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.savedRecipeExists(userId, recipeId)
    }

    suspend fun saveRecipe(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.saveRecipe(userId, recipeId)
    }

    suspend fun unsaveRecipe(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.unsaveRecipe(userId, recipeId)
    }

    suspend fun checkLikedRecipeExists(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.likedRecipeExists(userId, recipeId)
    }

    suspend fun likeRecipe(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.likeRecipe(userId, recipeId)
    }

    suspend fun unlikeRecipe(userId: Long, recipeId: Long) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.unlikeRecipe(userId, recipeId)
    }
}