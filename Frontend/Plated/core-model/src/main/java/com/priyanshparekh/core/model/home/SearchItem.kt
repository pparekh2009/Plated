package com.priyanshparekh.core.model.home

data class SearchItem(
    val header: String? = null,
    val recipe: RecipeSearchItem? = null,
    val user: UserSearchItem? = null,
    val type: ItemType
)

enum class ItemType {
    Header, Recipe, User
}
