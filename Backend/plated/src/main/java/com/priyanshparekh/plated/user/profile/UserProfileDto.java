package com.priyanshparekh.plated.user.profile;

import com.priyanshparekh.plated.recipe.dto.ProfileRecipeItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {

    private Long id;
    private String displayName;
    private String bio;
    private String profession;
    private String website;
    private int recipeCount;
    private int followersCount;
    private int followingCount;
    private List<ProfileRecipeItem> recipes;

}
