package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.search.RecipeSearchItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<ProfileRecipeItemProjection> findAllByUserId(Long userId);

    List<RecipeSearchItemProjection> findAllByNameContaining(String query);
}
