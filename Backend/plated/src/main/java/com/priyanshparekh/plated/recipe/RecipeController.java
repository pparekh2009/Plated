package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.MessageResponse;
import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import com.priyanshparekh.plated.recipe.dto.ViewRecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class RecipeController {

    private final RecipeService recipeService;

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
}
