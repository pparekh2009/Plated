package com.priyanshparekh.plated.ingredient;

import com.priyanshparekh.plated.ingredient.dto.IngredientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

    Ingredient toEntity(IngredientDto ingredientDto);

    List<Ingredient> toEntityList(List<IngredientDto> ingredientDtoList);

    List<IngredientDto> toDtoList(List<Ingredient> ingredientList);
}
