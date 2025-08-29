package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.recipe.dto.AddRecipeRequest;
import com.priyanshparekh.plated.recipe.dto.AddRecipeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "recipeName")
    Recipe toEntity(AddRecipeRequest addRecipeRequest);

    AddRecipeResponse toDto(Recipe recipe);

}
