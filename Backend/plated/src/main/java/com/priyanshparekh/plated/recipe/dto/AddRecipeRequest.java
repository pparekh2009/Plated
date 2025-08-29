package com.priyanshparekh.plated.recipe.dto;

import com.priyanshparekh.plated.ingredient.Ingredient;
import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
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
public class AddRecipeRequest {

    private String recipeName;
    private String description;
    private int servingSize;
    private float cookingTime;
    private String cuisine;
    private String category;
    private List<RecipeIngredientDto> ingredientList;
    private List<StepsDto> stepList;

}
