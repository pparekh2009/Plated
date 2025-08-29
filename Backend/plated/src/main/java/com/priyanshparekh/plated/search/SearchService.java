package com.priyanshparekh.plated.search;

import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public SearchResultResponse getSearchResult(String query) {
        List<UserSearchItemProjection> users = userRepository.findAllByDisplayNameContaining(query);
        List<RecipeSearchItemProjection> recipes = recipeRepository.findAllByNameContaining(query);

        List<UserSearchItemDto> userSearchItemDtos = users.stream().map(userSearchItemProjection -> UserSearchItemDto.builder()
                .id(userSearchItemProjection.getId())
                .name(userSearchItemProjection.getDisplayName())
                .build()).toList();

        List<RecipeSearchItemDto> recipeSearchItemDtos = recipes.stream().map(recipeSearchItemProjection -> RecipeSearchItemDto.builder()
                .id(recipeSearchItemProjection.getId())
                .name(recipeSearchItemProjection.getName())
                .build()).toList();

        return SearchResultResponse.builder()
                .users(userSearchItemDtos)
                .recipes(recipeSearchItemDtos)
                .build();
    }

}
