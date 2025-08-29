package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @Column(name = "serving_size")
    private int servingSize;

    @Column(name = "cooking_time")
    private float cookingTime;

    private String cuisine;
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
