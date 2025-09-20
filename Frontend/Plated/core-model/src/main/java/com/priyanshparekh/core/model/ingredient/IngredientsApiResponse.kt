package com.priyanshparekh.core.model.ingredient

import com.google.gson.annotations.SerializedName

data class IngredientsApiResponse(
    @SerializedName("meals") val meals: List<Ingredient>
)