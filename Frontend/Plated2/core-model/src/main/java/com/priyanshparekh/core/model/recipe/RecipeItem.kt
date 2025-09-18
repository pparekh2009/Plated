package com.priyanshparekh.core.model.recipe

data class RecipeItem(
    val recipeId: Long,
    val recipeName: String,
    val cookingTime: Float,
    val displayName: String
)