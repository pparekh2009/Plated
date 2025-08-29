package com.priyanshparekh.plated.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priyanshparekh.plated.auth.AuthService;
import com.priyanshparekh.plated.config.TestSecurityConfig;
import com.priyanshparekh.plated.ingredient.IngredientController;
import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import com.priyanshparekh.plated.recipe.recipeingredient.dto.RecipeIngredientDto;
import com.priyanshparekh.plated.recipe.step.StepsDto;
import com.priyanshparekh.plated.security.JwtAuthenticationFilter;
import com.priyanshparekh.plated.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeController recipeController;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    public void addRecipeEndpointTest() throws Exception {
        // Arrange
        AddRecipeResponse recipe = AddRecipeResponse.builder()
                .id(1L)
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


        String addRecipeRequestJson = objectMapper.writeValueAsString(addRecipeRequest);

        when(recipeService.addRecipe(addRecipeRequest)).thenReturn(recipe);

        // Act + Assert
        mockMvc.perform(
                post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addRecipeRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(addRecipeRequest.getRecipeName()));
    }

}
