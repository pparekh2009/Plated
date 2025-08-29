package com.priyanshparekh.plated.ingredient;

import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IngredientMapperTest {

    @Autowired
    IngredientMapper ingredientMapper;

    @Test
    public void ingredient_fromDto_toEntity_Test() {
        // Arrange
        IngredientDto ingredientDto = new IngredientDto(1L, "Potato");

        // Act
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);

        // Assert
        assertNotNull(ingredient);
        assertEquals(ingredientDto.getId(), ingredient.getId());
    }

    @Test
    public void ingredientList_fromDto_toEntity_Test() {
        // Arrange
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(new IngredientDto(1L, "Potato"));
        ingredientDtoList.add(new IngredientDto(2L, "Tomato"));
        ingredientDtoList.add(new IngredientDto(3L, "Flour"));

        // Act
        List<Ingredient> ingredientList = ingredientMapper.toEntityList(ingredientDtoList);

        // Assert
        assertNotNull(ingredientList);
        assertEquals(ingredientDtoList.size(), ingredientList.size());
    }

    @Test
    public void ingredientList_fromEntity_toDto_Test() {
        // Arrange
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(1L, "Potato"));
        ingredientList.add(new Ingredient(2L, "Tomato"));
        ingredientList.add(new Ingredient(3L, "Flour"));

        // Act
        List<IngredientDto> ingredientDtoList = ingredientMapper.toDtoList(ingredientList);

        // Assert
        assertNotNull(ingredientDtoList);
        assertEquals(ingredientDtoList.size(), ingredientList.size());
    }

}
