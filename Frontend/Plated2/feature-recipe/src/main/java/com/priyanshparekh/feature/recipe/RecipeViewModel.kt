package com.priyanshparekh.feature.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.model.MessageResponse
import com.priyanshparekh.core.model.recipe.AddRecipeRequest
import com.priyanshparekh.core.model.recipe.AddRecipeResponse
import com.priyanshparekh.core.model.recipe.IngredientDto
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.RecipeItem
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


    private val _savedRecipesStatus = MutableLiveData<Status<List<RecipeItem>>>()
    val savedRecipesStatus: LiveData<Status<List<RecipeItem>>> = _savedRecipesStatus

    fun getSavedRecipes(userId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.getSavedRecipes(userId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _savedRecipesStatus.value = Status.SUCCESS(body)
                } else {
                    _savedRecipesStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "getSavedRecipes: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "getSavedRecipes: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _savedRecipesStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "getSavedRecipes: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "getSavedRecipes: recipeViewModel: outer if: message: $errorString")
            }
        }
    }



    private val _saveRecipeStatus = MutableLiveData<Status<MessageResponse>>()
    val saveRecipeStatus: LiveData<Status<MessageResponse>> = _saveRecipeStatus

    fun saveRecipe(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.saveRecipe(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _saveRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _saveRecipeStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "saveRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "saveRecipe: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _saveRecipeStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "saveRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "saveRecipe: recipeViewModel: outer if: message: $errorString")
            }
        }
    }


    private val _unsaveRecipeStatus = MutableLiveData<Status<MessageResponse>>()
    val unsaveRecipeStatus: LiveData<Status<MessageResponse>> = _unsaveRecipeStatus

    fun unsaveRecipe(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.unsaveRecipe(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _unsaveRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _unsaveRecipeStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "saveRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "saveRecipe: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _unsaveRecipeStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "saveRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "saveRecipe: recipeViewModel: outer if: message: $errorString")
            }
        }
    }



    private val _savedRecipeExistsStatus = MutableLiveData<Status<Boolean>>()
    val savedRecipeExistsStatus: LiveData<Status<Boolean>> = _savedRecipeExistsStatus

    fun savedRecipeExists(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.checkSavedRecipeExists(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _savedRecipeExistsStatus.value = Status.SUCCESS(body)
                } else {
                    _savedRecipeExistsStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "savedRecipeExists: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "savedRecipeExists: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _savedRecipeExistsStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "savedRecipeExists: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "savedRecipeExists: recipeViewModel: outer if: message: $errorString")
            }
        }
    }
}