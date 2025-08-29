package com.priyanshparekh.core.model.ingredient

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient") val id: Int,
    @SerializedName("strIngredient") val name: String
)