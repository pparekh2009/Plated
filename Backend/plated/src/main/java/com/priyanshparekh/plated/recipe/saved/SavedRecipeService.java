package com.priyanshparekh.plated.recipe.saved;

import com.priyanshparekh.plated.recipe.Recipe;
import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.recipe.dto.RecipeItem;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavedRecipeService {

    private final SavedRecipeRepository savedRecipeRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public List<RecipeItem> getSavedRecipes(Long userId) {
        List<SavedRecipeItemProjection> savedRecipeItemProjections = savedRecipeRepository.findAllByUserId(userId);
        log.info("savedRecipeService: getSavedRecipes: savedRecipeItemProjections: size: {}", savedRecipeItemProjections.size());
        return savedRecipeItemProjections.stream().map( projections -> {
            log.info("savedRecipeService: getSavedRecipes: projection: recipe name{}", projections.getRecipe().getName());
            log.info("savedRecipeService: getSavedRecipes: projection: user name{}", projections.getRecipe().getUser().getDisplayName());
            return RecipeItem.builder()
                    .recipeId(projections.getRecipe().getId())
                    .recipeName(projections.getRecipe().getName())
                    .cookingTime(projections.getRecipe().getCookingTime())
                    .displayName(projections.getRecipe().getUser().getDisplayName())
                    .build();
        }).toList();
    }

    public void saveRecipe(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        SavedRecipe recipeToSave = new SavedRecipe();
        recipeToSave.setUser(user);
        recipeToSave.setRecipe(recipe);

        savedRecipeRepository.save(recipeToSave);
    }

    @Transactional
    public void unsaveRecipe(Long userId, Long recipeId) {
        savedRecipeRepository.deleteByUser_IdAndRecipe_Id(userId, recipeId);
    }

    public Boolean savedRecipeExists(Long userId, Long recipeId) {
        return savedRecipeRepository.existsByUser_IdAndRecipe_Id(userId, recipeId);
    }
}
