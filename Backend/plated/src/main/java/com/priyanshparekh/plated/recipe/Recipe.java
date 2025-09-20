package com.priyanshparekh.plated.recipe;

import com.priyanshparekh.plated.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "created_at")
    private Long createdAt;

    @PrePersist
    void setCreatedDate() {
        createdAt = System.currentTimeMillis();
    }
}
