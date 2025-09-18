package com.priyanshparekh.plated.recipe;

public interface RecipeItemProjection {

    Long getId();
    String getName();
    float getCookingTime();
    UserNameProjection getUser();

}
