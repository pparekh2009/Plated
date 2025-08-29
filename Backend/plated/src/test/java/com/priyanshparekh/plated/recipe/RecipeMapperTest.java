package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RecipeMapperTest {

    @Autowired
    private RecipeMapper recipeMapper;

    @Test
    public void Recipe_FromDto_ToEntity_Test() {
        // Arrange
        AddRecipeRequest addRecipeRequest = AddRecipeRequest.builder()
                .recipeName("Test Recipe")
                .category("Drink")
                .cookingTime(20F)
                .cuisine("Indian")
                .description("Test description")
                .servingSize(2)
                .build();

        // Act
        Recipe recipe = recipeMapper.toEntity(addRecipeRequest);

        // Assert
        assertNotNull(recipe);
        assertEquals(recipe.getName(), addRecipeRequest.getRecipeName());
    }

    @Test
    public void Recipe_FromEntity_ToDto_Test() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .name("Test Recipe")
                .category("Drink")
                .cookingTime(20F)
                .cuisine("Indian")
                .description("Test description")
                .servingSize(2)
                .build();

        // Act
        AddRecipeResponse addRecipeResponse = recipeMapper.toDto(recipe);

        // Assert
        assertNotNull(addRecipeResponse);
        assertEquals(addRecipeResponse.getName(), recipe.getName());
    }

}
