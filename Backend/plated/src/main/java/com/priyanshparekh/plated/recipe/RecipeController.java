package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.MessageResponse;
import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import com.priyanshparekh.plated.recipe.dto.RecipeItem;
import com.priyanshparekh.plated.recipe.dto.ViewRecipeResponse;
import com.priyanshparekh.plated.recipe.saved.SavedRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class RecipeController {

    private final RecipeService recipeService;
    private final SavedRecipeService savedRecipeService;

    @PostMapping("/recipes")
    ResponseEntity<AddRecipeResponse> addRecipe(@RequestBody AddRecipeRequest addRecipeRequest) {
        AddRecipeResponse addedRecipe = recipeService.addRecipe(addRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRecipe);
    }

    @GetMapping("/recipes/{recipe-id}")
    ResponseEntity<ViewRecipeResponse> getRecipe(@PathVariable("recipe-id") Long recipeId) {
        ViewRecipeResponse recipe = recipeService.getRecipe(recipeId);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/users/{user-id}/saved")
    ResponseEntity<List<RecipeItem>> getSavedRecipes(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(savedRecipeService.getSavedRecipes(userId));
    }

    @PostMapping("/users/{user-id}/save/{recipe-id}")
    ResponseEntity<MessageResponse> saveRecipe(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        savedRecipeService.saveRecipe(userId, recipeId);
        return ResponseEntity.ok(MessageResponse.builder().message("Recipe Saved").build());
    }

    @DeleteMapping("/users/{user-id}/unsave/{recipe-id}")
    ResponseEntity<MessageResponse> unsaveRecipe(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        savedRecipeService.unsaveRecipe(userId, recipeId);
        return ResponseEntity.ok(MessageResponse.builder().message("Recipe Unsaved").build());
    }

    @GetMapping("/users/{user-id}/exists/{recipe-id}")
    ResponseEntity<Boolean> saveExists(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        Boolean savedRecipeExists = savedRecipeService.savedRecipeExists(userId, recipeId);
        return ResponseEntity.ok(savedRecipeExists);
    }
}
