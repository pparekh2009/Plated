package com.priyanshparekh.core.model.profile

import com.priyanshparekh.core.model.recipe.RecipeItem

data class UserProfileDto(
    val id: Long,
    val displayName: String,
    val bio: String,
    val profession: String,
    val website: String,
    val recipeCount: Int,
    val followersCount: Int,
    val followingCount: Int,
    val recipes: List<RecipeItem>
)
