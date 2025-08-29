package com.priyanshparekh.plated.ingredient;

import com.priyanshparekh.plated.ingredient.dto.AddIngredientsRequest;
import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    private final IngredientRepo ingredientRepo;
    private final IngredientMapper ingredientMapper;

    public List<IngredientDto> addIngredients(AddIngredientsRequest addIngredientsRequest) {
        List<Ingredient> ingredients = ingredientMapper.toEntityList(addIngredientsRequest.getIngredientList());
        log.info("ingredientService: addIngredients: ingredients: {}", ingredients);

        List<Ingredient> savedIngredients = ingredientRepo.saveAll(ingredients);

        return ingredientMapper.toDtoList(savedIngredients);
    }

    public List<IngredientDto> getIngredients() {
        List<Ingredient> ingredients = ingredientRepo.findAll();
        return ingredientMapper.toDtoList(ingredients);
    }

}
