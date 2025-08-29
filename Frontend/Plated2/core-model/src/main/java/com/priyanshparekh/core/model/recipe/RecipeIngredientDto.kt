package com.priyanshparekh.core.model.recipe

data class RecipeIngredientDto(
    val id: Long,
    val name: String,
    val quantity: Float,
    val unit: String
)