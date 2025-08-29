package com.priyanshparekh.plated.recipe.recipeingredient;

import com.priyanshparekh.plated.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredient {

    @Id
    private Long id;
    private String name;
    private float quantity;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
