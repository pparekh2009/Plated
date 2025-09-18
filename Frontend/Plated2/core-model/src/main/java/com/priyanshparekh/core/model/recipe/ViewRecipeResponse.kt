package com.priyanshparekh.core.model.recipe

data class ViewRecipeResponse(
    val recipeId: Long,
    val name: String,
    val cuisine: String,
    val category: String,
    val cookingTime: Float,
    val servingSize: Int,
    val ingredients: List<RecipeIngredientDto>,
    val steps: List<StepDto>,
    val userId: Int,
    val userName: String
)