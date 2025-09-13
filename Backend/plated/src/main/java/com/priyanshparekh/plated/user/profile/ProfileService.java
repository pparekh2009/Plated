package com.priyanshparekh.plated.user.profile;

import com.priyanshparekh.plated.follow.FollowRepository;
import com.priyanshparekh.plated.recipe.ProfileRecipeItemProjection;
import com.priyanshparekh.plated.recipe.Recipe;
import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.recipe.dto.ProfileRecipeItem;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final FollowRepository followRepository;

    UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        List<ProfileRecipeItem> recipes = recipeRepository.findAllByUserId(userId)
                .stream()
                .map(projection ->
                        ProfileRecipeItem.builder()
                                .recipeId(projection.getId())
                                .recipeName(projection.getName())
                                .cookingTime(projection.getCookingTime())
                                .build())
                .toList();

        recipes.forEach( recipe -> {
            log.info("ProfileService: getUserProfile: recipe: cookingTime: {}", recipe.getCookingTime());
        });

        int followersCount = followRepository.countByFollowingId(userId);
        int followingCount = followRepository.countByFollowerId(userId);

        return UserProfileDto.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profession(user.getProfession())
                .website(user.getWebsite())
                .recipes(recipes)
                .recipeCount(recipes.size())
                .followersCount(followersCount)
                .followingCount(followingCount)
                .build();
    }
}
