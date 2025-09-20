package com.priyanshparekh.core.model.recipe

data class AddRecipeResponse(
    val id: Long,
    val name: String,
    val description: String,
    val cuisine: String,
    val category: String,
    val servingSize: Int,
    val cookingTime: Float,
)
