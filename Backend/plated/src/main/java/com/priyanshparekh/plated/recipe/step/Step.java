package com.priyanshparekh.plated.recipe.step;

import com.priyanshparekh.plated.recipe.Recipe;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "steps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stepNo;
    private String step;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
