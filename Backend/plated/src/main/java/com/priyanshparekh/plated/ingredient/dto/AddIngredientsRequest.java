package com.priyanshparekh.plated.ingredient.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddIngredientsRequest {

    @NotNull
    private List<IngredientDto> ingredientList;

}
