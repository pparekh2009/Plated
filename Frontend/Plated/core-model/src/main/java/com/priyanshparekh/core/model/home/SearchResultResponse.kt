package com.priyanshparekh.core.model.home

data class SearchResultResponse(
    val recipes: List<RecipeSearchItem>,
    val users: List<UserSearchItem>
)
