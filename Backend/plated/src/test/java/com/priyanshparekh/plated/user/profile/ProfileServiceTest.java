package com.priyanshparekh.plated.user.profile;

import com.priyanshparekh.plated.auth.AuthService;
import com.priyanshparekh.plated.recipe.ProfileRecipeItemProjection;
import com.priyanshparekh.plated.recipe.RecipeRepository;
import com.priyanshparekh.plated.security.JwtAuthenticationFilter;
import com.priyanshparekh.plated.security.JwtService;
import com.priyanshparekh.plated.user.User;
import com.priyanshparekh.plated.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private RecipeRepository recipeRepository;

    @Test
    public void getUserProfileTest() {
        // Arrange
        User user = new User(1L, "test@email.com", "password", "John Doe", "Food lover", "Chef", "https://johnsrecipes.com");

        ProfileRecipeItemProjection recipe1 = Mockito.mock(ProfileRecipeItemProjection.class);
        when(recipe1.getName()).thenReturn("Pasta");
        when(recipe1.getCookingTime()).thenReturn(30f);

        ProfileRecipeItemProjection recipe2 = Mockito.mock(ProfileRecipeItemProjection.class);
        when(recipe2.getName()).thenReturn("Salad");
        when(recipe2.getCookingTime()).thenReturn(10f);

        List<ProfileRecipeItemProjection> recipeProjections = List.of(recipe1, recipe2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.findAllByUserId(1L)).thenReturn(recipeProjections);

        // Act
        UserProfileDto userProfileDto = profileService.getUserProfile(1L);

        // Assert
        assertEquals("John Doe", userProfileDto.getDisplayName());
        assertEquals("Food lover", userProfileDto.getBio());
        assertEquals("Chef", userProfileDto.getProfession());
        assertEquals("https://johnsrecipes.com", userProfileDto.getWebsite());
        assertEquals(2, userProfileDto.getRecipeCount());

        assertEquals("Pasta", userProfileDto.getRecipes().get(0).getRecipeName());
        assertEquals(30f, userProfileDto.getRecipes().get(0).getCookingTime());

        assertEquals("Salad", userProfileDto.getRecipes().get(1).getRecipeName());
        assertEquals(10f, userProfileDto.getRecipes().get(1).getCookingTime());
    }
}
