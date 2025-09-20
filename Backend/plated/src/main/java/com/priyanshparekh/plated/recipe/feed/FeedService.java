package com.priyanshparekh.plated.recipe.feed;

import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.recipe.dto.RecipeItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final RecipeRepository recipeRepository;

//    public List<RecipeItem> getTrendingRecipes() {
//        recipeRepository.findAllBy
//    }
}
