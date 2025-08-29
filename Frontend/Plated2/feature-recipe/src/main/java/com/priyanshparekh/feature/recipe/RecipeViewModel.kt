package com.priyanshparekh.feature.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.model.recipe.AddRecipeRequest
import com.priyanshparekh.core.model.recipe.AddRecipeResponse
import com.priyanshparekh.core.model.recipe.IngredientDto
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.model.recipe.ViewRecipeResponse
import com.priyanshparekh.core.network.RetrofitInstance
import com.priyanshparekh.core.network.Status
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {

    private val recipeRepository = RecipeRepository()


    private val _recipe = MutableLiveData(AddRecipeRequest())
    val recipe: LiveData<AddRecipeRequest> = _recipe

    fun updateName(name: String) {
        _recipe.value = _recipe.value?.copy(recipeName = name)
    }

    fun updateDescription(description: String) {
        _recipe.value = _recipe.value?.copy(description = description)
    }

    fun updateServings(servings: Int) {
        _recipe.value = _recipe.value?.copy(servingSize = servings)
    }

    fun updateCookingTime(time: Long) {
        _recipe.value = _recipe.value?.copy(cookingTime = time)
    }

    fun updateCuisine(cuisine: String) {
        _recipe.value = _recipe.value?.copy(cuisine = cuisine)
    }

    fun updateCategory(category: String) {
        _recipe.value = _recipe.value?.copy(category = category)
    }

    fun updateIngredients(ingredients: List<RecipeIngredientDto>) {
        _recipe.value = _recipe.value?.copy(ingredientList = ingredients)
    }

    fun updateSteps(steps: List<StepDto>) {
        _recipe.value = _recipe.value?.copy(stepList = steps)
    }

    private val _ingredients = MutableLiveData<Status<List<IngredientDto>>>()
    val ingredients: LiveData<Status<List<IngredientDto>>> = _ingredients

    fun loadIngredients() {
        viewModelScope.launch {
            val response = recipeRepository.loadIngredients()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _ingredients.value = Status.SUCCESS(body)
                } else {
                    _ingredients.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
                }
            } else {
                _ingredients.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
            }
        }
    }

    private val _postRecipeStatus = MutableLiveData<Status<AddRecipeResponse>>()
    val postRecipeStatus: LiveData<Status<AddRecipeResponse>> = _postRecipeStatus

    fun postRecipe(addRecipeRequest: AddRecipeRequest) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.addRecipe(addRecipeRequest)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _postRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _postRecipeStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
                }
            } else {
                _postRecipeStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
            }
        }
    }


    private val _viewRecipeStatus = MutableLiveData<Status<ViewRecipeResponse>>()
    val viewRecipeStatus: LiveData<Status<ViewRecipeResponse>> = _viewRecipeStatus

    fun getRecipe(recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.getRecipe(recipeId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _viewRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _viewRecipeStatus.value = Status.ERROR("Error getting recipe")
                    Log.d("TAG", "getRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "getRecipe: recipeViewModel: inner if: message: ${response.message()}")
                }
            } else {
                _viewRecipeStatus.value = Status.ERROR("Error getting recipe")
                Log.d("TAG", "getRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "getRecipe: recipeViewModel: outer if: message: ${response.message()}")
            }
        }
    }
}