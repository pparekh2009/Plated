package com.priyanshparekh.plated.recipe.liked;

import com.priyanshparekh.plated.recipe.saved.SavedRecipe;
import com.priyanshparekh.plated.recipe.saved.SavedRecipeItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedRecipeRepository extends JpaRepository<LikedRecipe, Long> {
//    List<SavedRecipeItemProjection> findAllByUserId(Long userId);

    void deleteByUser_IdAndRecipe_Id(Long userId, Long recipeId);

    Boolean existsByUser_IdAndRecipe_Id(Long userId, Long recipeId);
}
