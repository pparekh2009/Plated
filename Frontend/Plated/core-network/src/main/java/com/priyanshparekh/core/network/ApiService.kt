package com.priyanshparekh.core.network

import com.priyanshparekh.core.model.MessageResponse
import com.priyanshparekh.core.model.auth.UserLoginRequest
import com.priyanshparekh.core.model.auth.UserLoginResponse
import com.priyanshparekh.core.model.auth.UserSignUpRequest
import com.priyanshparekh.core.model.home.SearchResultResponse
import com.priyanshparekh.core.model.ingredient.AddIngredientsRequest
import com.priyanshparekh.core.model.profile.FollowRelation
import com.priyanshparekh.core.model.profile.UserProfileDto
import com.priyanshparekh.core.model.recipe.AddRecipeRequest
import com.priyanshparekh.core.model.recipe.AddRecipeResponse
import com.priyanshparekh.core.model.recipe.IngredientDto
import com.priyanshparekh.core.model.recipe.RecipeItem
import com.priyanshparekh.core.model.recipe.ViewRecipeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /** Auth **/

    @POST("auth/signup")
    suspend fun signUp(@Body userSignUpRequest: UserSignUpRequest): Response<UserSignUpRequest>

    @POST("auth/login")
    suspend fun login(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>


    @GET("search")
    suspend fun getSearchResults(@Query(value = "query") query: String): Response<SearchResultResponse>


    /** Ingredient **/

    @GET("ingredients")
    suspend fun loadIngredients(): Response<List<IngredientDto>>

    @POST("ingredients")
    suspend fun uploadIngredients(@Body addIngredientsRequest: AddIngredientsRequest): Response<MessageResponse>


    /** Recipe **/

    @POST("recipes")
    suspend fun addRecipe(@Body addRecipeRequest: AddRecipeRequest): Response<AddRecipeResponse>

    @GET("recipes/{recipe-id}")
    suspend fun getRecipe(@Path(value = "recipe-id") recipeId: Long): Response<ViewRecipeResponse>

    @POST("users/{user-id}/save/{recipe-id}")
    suspend fun saveRecipe(@Path(value = "user-id") userId: Long, @Path(value = "recipe-id") recipeId: Long): Response<MessageResponse>

    @DELETE("users/{user-id}/unsave/{recipe-id}")
    suspend fun unsaveRecipe(@Path(value = "user-id") userId: Long, @Path(value = "recipe-id") recipeId: Long): Response<MessageResponse>

    @GET("users/{user-id}/saved")
    suspend fun getSavedRecipes(@Path(value = "user-id") userId: Long): Response<List<RecipeItem>>

    @GET("users/{user-id}/exists/{recipe-id}")
    suspend fun savedRecipeExists(@Path(value = "user-id") userId: Long, @Path(value = "recipe-id") recipeId: Long): Response<Boolean>


    /** User **/

    @GET("users/{user-id}/profile")
    suspend fun getUserProfile(@Path(value = "user-id") userId: Long): Response<UserProfileDto>


    /** Follow **/

    @POST("users/{follower-id}/follow/{following-id}")
    suspend fun follow(@Path(value = "follower-id") followerId: Long, @Path(value = "following-id") followingId: Long): Response<MessageResponse>

    @DELETE("users/{follower-id}/unfollow/{following-id}")
    suspend fun unfollow(@Path(value = "follower-id") followerId: Long, @Path(value = "following-id") followingId: Long): Response<MessageResponse>

    @GET("users/{follower-id}/is-following/{following-id}")
    suspend fun isFollowing(@Path(value = "follower-id") followerId: Long, @Path(value = "following-id") followingId: Long): Response<Boolean>

    @GET("users/{user-id}/followers")
    suspend fun getFollowers(@Path(value = "user-id") userId: Long): Response<List<FollowRelation>>

    @GET("users/{user-id}/following")
    suspend fun getFollowing(@Path(value = "user-id") userId: Long): Response<List<FollowRelation>>


    /** Home **/

    @GET("recipes/trending")
    suspend fun getTrendingRecipes(): Response<List<RecipeItem>>
}