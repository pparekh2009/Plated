package com.priyanshparekh.plated.ingredient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priyanshparekh.plated.auth.AuthController;
import com.priyanshparekh.plated.auth.AuthService;
import com.priyanshparekh.plated.config.TestSecurityConfig;
import com.priyanshparekh.plated.ingredient.dto.AddIngredientsRequest;
import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import com.priyanshparekh.plated.security.JwtAuthenticationFilter;
import com.priyanshparekh.plated.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(IngredientController.class)
@Import(TestSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    IngredientService ingredientService;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    public void addIngredientsTest() throws Exception {
        // Arrange
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(new IngredientDto(1L, "Potato"));
        ingredientDtoList.add(new IngredientDto(2L, "Tomato"));
        ingredientDtoList.add(new IngredientDto(3L, "Flour"));

        AddIngredientsRequest addIngredientsRequest = new AddIngredientsRequest(ingredientDtoList);

        String addIngredientsRequestJson = objectMapper.writeValueAsString(addIngredientsRequest);
        log.info("ingredientControllerTest: addIngredientsTest: addIngredientsRequestJson: {}", addIngredientsRequestJson);

        when(ingredientService.addIngredients(addIngredientsRequest)).thenReturn(ingredientDtoList);

        // Act & Assert
        mockMvc.perform(
                post("/api/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addIngredientsRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Ingredients Added"));

    }

    @Test
    public void getIngredientsTest() throws Exception {
        // Arrange
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(new IngredientDto(1L, "Potato"));
        ingredientDtoList.add(new IngredientDto(2L, "Tomato"));
        ingredientDtoList.add(new IngredientDto(3L, "Flour"));

        when(ingredientService.getIngredients()).thenReturn(ingredientDtoList);

        // Act + Assert
        mockMvc.perform(get("/api/v1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
