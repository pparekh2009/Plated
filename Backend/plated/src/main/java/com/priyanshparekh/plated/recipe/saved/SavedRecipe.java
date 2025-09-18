package com.priyanshparekh.plated.recipe.saved;

import com.priyanshparekh.plated.recipe.Recipe;
import com.priyanshparekh.plated.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "saved_recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
