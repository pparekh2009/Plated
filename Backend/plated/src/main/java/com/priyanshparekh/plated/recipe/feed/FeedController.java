package com.priyanshparekh.plated.recipe.feed;

import com.priyanshparekh.plated.recipe.dto.RecipeItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

//    @GetMapping("recipes/trending")
//    ResponseEntity<List<RecipeItem>> getTrendingRecipes() {
//        return ResponseEntity.ok(feedService.getTrendingRecipes());
//    }

}
