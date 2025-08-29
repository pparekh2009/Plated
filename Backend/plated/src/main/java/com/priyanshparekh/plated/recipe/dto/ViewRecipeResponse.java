package com.priyanshparekh.plated.recipe.dto;

import com.priyanshparekh.plated.recipe.recipeingredient.dto.RecipeIngredientDto;
import com.priyanshparekh.plated.recipe.step.StepsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewRecipeResponse {

    private Long recipeId;
    private String name;
    private String cuisine;
    private String category;
    private float cookingTime;
    private int servingSize;
    private List<RecipeIngredientDto> ingredients;
    private List<StepsDto> steps;
    private Long userId;
    private String userName;

}
