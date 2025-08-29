package com.priyanshparekh.plated.ingredient;

import com.priyanshparekh.plated.ingredient.dto.AddIngredientsRequest;
import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @Mock
    private IngredientMapper ingredientMapper;

    @Test
    public void addIngredientsTest() {
        // Arrange
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(new IngredientDto(1L, "Potato"));
        ingredientDtoList.add(new IngredientDto(2L, "Tomato"));
        ingredientDtoList.add(new IngredientDto(3L, "Flour"));

        AddIngredientsRequest addIngredientsRequest = new AddIngredientsRequest(ingredientDtoList);

        List<Ingredient> ingredientList = ingredientDtoList.stream().map(ingredientDto -> ingredientMapper.toEntity(ingredientDto)).toList();

        when(ingredientMapper.toEntityList(ingredientDtoList)).thenReturn(ingredientList);
        when(ingredientMapper.toDtoList(ingredientList)).thenReturn(ingredientDtoList);

        // Act
        List<IngredientDto> savedIngredients = ingredientService.addIngredients(addIngredientsRequest);

        // Assert
        assertNotNull(savedIngredients);
        assertEquals(ingredientDtoList.size(), savedIngredients.size());
    }

    @Test
    public void getIngredients_returnIngredientsList() {
        // Arrange
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(new IngredientDto(1L, "Potato"));
        ingredientDtoList.add(new IngredientDto(2L, "Tomato"));
        ingredientDtoList.add(new IngredientDto(3L, "Flour"));

        List<Ingredient> ingredientList = ingredientDtoList.stream().map(ingredientDto -> ingredientMapper.toEntity(ingredientDto)).toList();

        when(ingredientMapper.toDtoList(ingredientList)).thenReturn(ingredientDtoList);

        // Act
        List<IngredientDto> ingredients = ingredientService.getIngredients();
        log.info("ingredientServiceTest: getIngredients_returnIngredientsList: ingredients: {}", ingredients);

        // Assert
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
    }
}
