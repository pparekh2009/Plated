package com.priyanshparekh.core.model.recipe

sealed class RecipeItem {

    data class ProfileRecipeItem(
        val recipeId: Long,
        val recipeName: String,
        val cookingTime: Float,
    ): RecipeItem()

    data class FeedRecipeItem(
        val recipeId: Long,
        val recipeName: String,
        val time: Float,
        val username: String
    ): RecipeItem()

}