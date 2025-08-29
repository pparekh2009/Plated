package com.priyanshparekh.plated.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRecipeItem {

    private Long recipeId;
    private String recipeName;
    private float cookingTime;

}
