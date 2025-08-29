package com.priyanshparekh.core.model.recipe

data class IngredientDto(
    val id: Long,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
