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
public class RecipePreparationResponse {

    private List<RecipeIngredientDto> ingredients;
    private List<StepsDto> steps;

}
