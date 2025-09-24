package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import com.priyanshparekh.plated.recipe.dto.RecipeDetailResponse;
import com.priyanshparekh.plated.recipe.dto.RecipePreparationResponse;
import com.priyanshparekh.plated.recipe.recipeingredient.RecipeIngredient;
import com.priyanshparekh.plated.recipe.recipeingredient.RecipeIngredientRepository;
import com.priyanshparekh.plated.recipe.recipeingredient.dto.RecipeIngredientDto;
import com.priyanshparekh.plated.recipe.step.Step;
import com.priyanshparekh.plated.recipe.step.StepRepository;
import com.priyanshparekh.plated.recipe.step.StepsDto;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeMapper recipeMapper;

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;

    AddRecipeResponse addRecipe(AddRecipeRequest addRecipeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Recipe recipe = recipeMapper.toEntity(addRecipeRequest);
        recipe.setUser(user);

        Recipe savedRecipe = recipeRepository.save(recipe);

        List<RecipeIngredientDto> recipeIngredientDtoList = addRecipeRequest.getIngredientList();
        List<StepsDto> stepsDtoList = addRecipeRequest.getStepList();

        System.out.println("Ingredient List: " + recipeIngredientDtoList);
        System.out.println("Steps List: " + stepsDtoList);

        if (recipeIngredientDtoList == null) {
            return null;
        }

        List<RecipeIngredient> recipeIngredientList = addRecipeRequest.getIngredientList().stream().map(ingredientDto -> RecipeIngredient.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .unit(ingredientDto.getUnit())
                .recipe(savedRecipe)
                .build()).toList();
        recipeIngredientRepository.saveAll(recipeIngredientList);

        if (stepsDtoList == null) {
            return null;
        }

        List<Step> stepList = addRecipeRequest.getStepList().stream().map(stepDto -> Step.builder()
                .stepNo(stepDto.getStepNo())
                .step(stepDto.getStep())
                .recipe(savedRecipe)
                .build()).toList();
        stepRepository.saveAll(stepList);

        return recipeMapper.toDto(savedRecipe);
    }

    public RecipeDetailResponse getRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        return RecipeDetailResponse.builder()
                .recipeId(recipe.getId())
                .name(recipe.getName())
                .cuisine(recipe.getCuisine())
                .category(recipe.getCategory())
                .cookingTime(recipe.getCookingTime())
                .servingSize(recipe.getServingSize())
                .userName(recipe.getUser().getDisplayName())
                .userId(recipe.getUser().getId())
                .build();
    }

    public RecipePreparationResponse getRecipePreparationData(Long recipeId) {
        List<RecipeIngredientDto> ingredients = recipeIngredientRepository
                .findAllByRecipeId(recipeId)
                .stream()
                .map(recipeIngredient -> RecipeIngredientDto.builder()
                        .id(recipeIngredient.getId())
                        .name(recipeIngredient.getName())
                        .quantity(recipeIngredient.getQuantity())
                        .unit(recipeIngredient.getUnit())
                        .build())
                .toList();
        List<StepsDto> steps = stepRepository
                .findAllByRecipeId(recipeId)
                .stream()
                .map(step -> StepsDto.builder()
                        .step(step.getStep())
                        .stepNo(step.getStepNo())
                        .build())
                .toList();

        return RecipePreparationResponse.builder()
                .ingredients(ingredients)
                .steps(steps)
                .build();
    }
}
