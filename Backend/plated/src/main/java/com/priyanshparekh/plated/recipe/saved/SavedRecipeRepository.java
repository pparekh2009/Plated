package com.priyanshparekh.plated.recipe.saved;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, Long> {
    List<SavedRecipeItemProjection> findAllByUserId(Long userId);

    void deleteByUser_IdAndRecipe_Id(Long userId, Long recipeId);

    Boolean existsByUser_IdAndRecipe_Id(Long userId, Long recipeId);
}
