package com.priyanshparekh.plated.user.profile;

import com.priyanshparekh.plated.auth.AuthService;
import com.priyanshparekh.plated.config.TestSecurityConfig;
import com.priyanshparekh.plated.recipe.dto.ProfileRecipeItem;
import com.priyanshparekh.plated.security.JwtAuthenticationFilter;
import com.priyanshparekh.plated.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    public void getUserProfileTest() throws Exception {
        // Arrange
        UserProfileDto mockProfile = new UserProfileDto();
        mockProfile.setDisplayName("John Doe");
        mockProfile.setBio("Food lover and chef");
        mockProfile.setProfession("Chef");
        mockProfile.setWebsite("https://johndoe.com");
        mockProfile.setRecipeCount(2);
        mockProfile.setFollowersCount(150);
        mockProfile.setFollowingCount(75);

        List<ProfileRecipeItem> mockRecipes = List.of(
                ProfileRecipeItem.builder()
                        .recipeName("Spaghetti Bolognese")
                        .cookingTime(30F)
                        .build(),
                ProfileRecipeItem.builder()
                        .recipeName("Pancakes")
                        .cookingTime(15F)
                        .build()
        );

        mockProfile.setRecipes(mockRecipes);

        when(profileService.getUserProfile(1L)).thenReturn(mockProfile);

        // Act + Assert
        mockMvc.perform(get("/api/v1/users/{user-id}/profile", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profession").value("Chef"))
                .andExpect(jsonPath("$.recipes").isNotEmpty())
                .andExpect(jsonPath("$.recipeCount").value(2));
    }

}
