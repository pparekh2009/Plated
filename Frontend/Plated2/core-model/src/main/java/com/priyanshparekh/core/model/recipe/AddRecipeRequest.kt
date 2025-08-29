package com.priyanshparekh.core.model.recipe

data class AddRecipeRequest(
    val userId: Long,
    var recipeName: String,
    val description: String,
    val servingSize: Int,
    val cookingTime: Long,
    val cuisine: String,
    val category: String,
    val ingredientList: List<RecipeIngredientDto>,
    val stepList: List<StepDto>
) {
    constructor(): this(-1L, "", "", 0, 0L, "", "", listOf(), listOf())
}
