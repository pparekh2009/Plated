package com.priyanshparekh.plated.recipe.liked;

import com.priyanshparekh.plated.recipe.Recipe;
import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.recipe.dto.RecipeItem;
import com.priyanshparekh.plated.recipe.saved.SavedRecipe;
import com.priyanshparekh.plated.recipe.saved.SavedRecipeItemProjection;
import com.priyanshparekh.plated.recipe.saved.SavedRecipeRepository;
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
public class LikedRecipeService {

    private final LikedRecipeRepository likedRecipeRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public void likeRecipe(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        LikedRecipe recipeToLike = new LikedRecipe();
        recipeToLike.setUser(user);
        recipeToLike.setRecipe(recipe);

        likedRecipeRepository.save(recipeToLike);
    }

    @Transactional
    public void unlikeRecipe(Long userId, Long recipeId) {
        likedRecipeRepository.deleteByUser_IdAndRecipe_Id(userId, recipeId);
    }

    public Boolean likedRecipeExists(Long userId, Long recipeId) {
        return likedRecipeRepository.existsByUser_IdAndRecipe_Id(userId, recipeId);
    }
}
