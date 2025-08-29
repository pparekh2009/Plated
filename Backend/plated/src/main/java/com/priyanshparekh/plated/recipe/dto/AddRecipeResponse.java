package com.priyanshparekh.plated.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRecipeResponse {

    private Long id;
    private String name;
    private String description;
    private String cuisine;
    private String category;
    private int servingSize;
    private float cookingTime;

}
