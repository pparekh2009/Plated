package com.priyanshparekh.plated.recipe.liked;

import com.priyanshparekh.plated.recipe.Recipe;
import com.priyanshparekh.plated.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "liked_recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikedRecipe {

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
