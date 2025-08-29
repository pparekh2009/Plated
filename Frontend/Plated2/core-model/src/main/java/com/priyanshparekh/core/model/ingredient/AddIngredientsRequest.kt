package com.priyanshparekh.core.model.ingredient

import com.priyanshparekh.core.model.recipe.IngredientDto

data class AddIngredientsRequest(
    val ingredientList: List<IngredientDto>
)