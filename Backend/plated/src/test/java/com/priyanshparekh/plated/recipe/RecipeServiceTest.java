package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import com.priyanshparekh.plated.recipe.recipeingredient.dto.RecipeIngredientDto;
import com.priyanshparekh.plated.recipe.step.StepsDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;

    @Mock
    private RecipeMapper recipeMapper;

    @Test
    public void addRecipeTest() {
        // Arrange
        Recipe recipe = Recipe.builder()
                .name("Test Recipe")
                .description("Test Description")
                .cookingTime(20F)
                .servingSize(2)
                .cuisine("Indian")
                .category("Dessert")
                .build();

        AddRecipeRequest addRecipeRequest = AddRecipeRequest.builder()
                .recipeName("Test Recipe")
                .description("Test Description")
                .cookingTime(20F)
                .servingSize(2)
                .cuisine("Indian")
                .category("Dessert")
                .ingredientList(new ArrayList<>(List.of(new RecipeIngredientDto(1L, "Potato", 2, "whole"), new RecipeIngredientDto(2L, "Tomato", 4, "kilograms"))))
                .stepList(new ArrayList<>(List.of(new StepsDto(1, "Prepare"), new StepsDto(2, "Cook"), new StepsDto(3, "Serve"))))
                .build();

        when(recipeMapper.toEntity(addRecipeRequest)).thenReturn(recipe);

        // Act
        AddRecipeResponse savedRecipe = recipeService.addRecipe(addRecipeRequest);

        // Assert
        assertNotNull(savedRecipe);
        assertEquals(savedRecipe.getName(), recipe.getName());
        assertNotNull(savedRecipe.getId());
    }

}
