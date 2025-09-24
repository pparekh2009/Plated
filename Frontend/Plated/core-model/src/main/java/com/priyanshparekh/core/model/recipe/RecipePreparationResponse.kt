package com.priyanshparekh.core.model.recipe

data class RecipePreparationResponse(
    val recipeId: Long,
    val ingredients: List<RecipeIngredientDto>,
    val steps: List<StepDto>
)
