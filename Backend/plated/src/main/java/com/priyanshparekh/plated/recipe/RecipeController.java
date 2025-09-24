package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.MessageResponse;
import com.priyanshparekh.plated.recipe.dto.*;
import com.priyanshparekh.plated.recipe.liked.LikedRecipeService;
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
    private final LikedRecipeService likedRecipeService;

    @PostMapping("/recipes")
    ResponseEntity<AddRecipeResponse> addRecipe(@RequestBody AddRecipeRequest addRecipeRequest) {
        AddRecipeResponse addedRecipe = recipeService.addRecipe(addRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRecipe);
    }

    @GetMapping("/recipes/{recipe-id}")
    ResponseEntity<RecipeDetailResponse> getRecipe(@PathVariable("recipe-id") Long recipeId) {
        RecipeDetailResponse recipe = recipeService.getRecipe(recipeId);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("recipes/{recipe-id}/preparation-data")
    ResponseEntity<RecipePreparationResponse> getRecipePreparationData(@PathVariable("recipe-id") Long recipeId) {
        RecipePreparationResponse recipePreparationResponse = recipeService.getRecipePreparationData(recipeId);
        return ResponseEntity.ok(recipePreparationResponse);
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

    @GetMapping("/users/{user-id}/is-saved/{recipe-id}")
    ResponseEntity<Boolean> saveExists(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        Boolean savedRecipeExists = savedRecipeService.savedRecipeExists(userId, recipeId);
        return ResponseEntity.ok(savedRecipeExists);
    }

    @PostMapping("/users/{user-id}/like/{recipe-id}")
    ResponseEntity<MessageResponse> likeRecipe(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        likedRecipeService.likeRecipe(userId, recipeId);
        return ResponseEntity.ok(MessageResponse.builder().message("Recipe Liked").build());
    }

    @DeleteMapping("/users/{user-id}/unlike/{recipe-id}")
    ResponseEntity<MessageResponse> unlikeRecipe(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        likedRecipeService.unlikeRecipe(userId, recipeId);
        return ResponseEntity.ok(MessageResponse.builder().message("Recipe Unliked").build());
    }

    @GetMapping("/users/{user-id}/is-liked/{recipe-id}")
    ResponseEntity<Boolean> likeExists(@PathVariable("user-id") Long userId, @PathVariable("recipe-id") Long recipeId) {
        Boolean likedRecipeExists = likedRecipeService.likedRecipeExists(userId, recipeId);
        return ResponseEntity.ok(likedRecipeExists);
    }
}
