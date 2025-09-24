package com.priyanshparekh.feature.recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.database.PlatedDb
import com.priyanshparekh.core.database.entity.Ingredient
import com.priyanshparekh.core.model.MessageResponse
import com.priyanshparekh.core.model.recipe.AddRecipeRequest
import com.priyanshparekh.core.model.recipe.AddRecipeResponse
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.RecipeItem
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.model.recipe.RecipeDetailResponse
import com.priyanshparekh.core.model.recipe.RecipePreparationResponse
import com.priyanshparekh.core.network.RetrofitInstance
import com.priyanshparekh.core.network.Status
import kotlinx.coroutines.launch

class RecipeViewModel(app: Application): AndroidViewModel(app) {

    private val recipeRepository = RecipeRepository()

    val ingredientDao = PlatedDb.getDatabase(app.applicationContext).ingredientDAO()

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

    fun resetRecipe() {
        _recipe.value = AddRecipeRequest()
    }




    private val _ingredients = MutableLiveData<Status<List<Ingredient>>>()
    val ingredients: LiveData<Status<List<Ingredient>>> = _ingredients

    fun loadIngredients() {
        viewModelScope.launch {

            val ingredients = ingredientDao.getAllIngredients()
            Log.d("TAG", "recipeViewModel: loadIngredients: ingredients size: ${ingredients.size}")
            Log.d("TAG", "recipeViewModel: loadIngredients: ingredients: $ingredients")

            _ingredients.value = Status.SUCCESS(ingredients)
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


    private val _viewRecipeStatus = MutableLiveData<Status<RecipeDetailResponse>>()
    val viewRecipeStatus: LiveData<Status<RecipeDetailResponse>> = _viewRecipeStatus

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


    private val _recipePreparationStatus = MutableLiveData<Status<RecipePreparationResponse>>()
    val recipePreparationStatus: LiveData<Status<RecipePreparationResponse>> = _recipePreparationStatus

    fun getRecipePreparationData(recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.getRecipePreparationData(recipeId)
            val errorString = response.errorBody()?.string()
            val code = response.code()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _recipePreparationStatus.value = Status.SUCCESS(body)
                } else {
                    _recipePreparationStatus.value = Status.ERROR("Error getting recipe")
                    Log.d("TAG", "getRecipePreparationData: recipeViewModel: inner if: code: $code")
                    Log.d("TAG", "getRecipePreparationData: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _recipePreparationStatus.value = Status.ERROR("Error getting recipe")
                Log.d("TAG", "getRecipePreparationData: recipeViewModel: outer if: code: $code")
                Log.d("TAG", "getRecipePreparationData: recipeViewModel: outer if: message: $errorString")
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
                    Log.d("TAG", "unsaveRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "unsaveRecipe: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _unsaveRecipeStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "unsaveRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "unsaveRecipe: recipeViewModel: outer if: message: $errorString")
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


    private val _likedRecipeExistsStatus = MutableLiveData<Status<Boolean>>()
    val likedRecipeExistsStatus: LiveData<Status<Boolean>> = _likedRecipeExistsStatus

    fun likedRecipeExists(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.checkLikedRecipeExists(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _likedRecipeExistsStatus.value = Status.SUCCESS(body)
                } else {
                    _likedRecipeExistsStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "likedRecipeExists: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "likedRecipeExists: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _likedRecipeExistsStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "likedRecipeExists: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "likedRecipeExists: recipeViewModel: outer if: message: $errorString")
            }
        }
    }



    private val _likeRecipeStatus = MutableLiveData<Status<MessageResponse>>()
    val likeRecipeStatus: LiveData<Status<MessageResponse>> = _likeRecipeStatus

    fun likeRecipe(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.likeRecipe(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _likeRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _likeRecipeStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "likeRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "likeRecipe: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _likeRecipeStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "likeRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "likeRecipe: recipeViewModel: outer if: message: $errorString")
            }
        }
    }


    private val _unlikeRecipeStatus = MutableLiveData<Status<MessageResponse>>()
    val unlikeRecipeStatus: LiveData<Status<MessageResponse>> = _unlikeRecipeStatus

    fun unlikeRecipe(userId: Long, recipeId: Long) {
        viewModelScope.launch {
            val response = recipeRepository.unlikeRecipe(userId, recipeId)
            val errorString = response.errorBody()?.string()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _unlikeRecipeStatus.value = Status.SUCCESS(body)
                } else {
                    _unlikeRecipeStatus.value = Status.ERROR("Error: $errorString")
                    Log.d("TAG", "unlikeRecipe: recipeViewModel: inner if: code: ${response.code()}")
                    Log.d("TAG", "unlikeRecipe: recipeViewModel: inner if: message: $errorString")
                }
            } else {
                _unlikeRecipeStatus.value = Status.ERROR("Error: $errorString")
                Log.d("TAG", "unlikeRecipe: recipeViewModel: outer if: code: ${response.code()}")
                Log.d("TAG", "unlikeRecipe: recipeViewModel: outer if: message: $errorString")
            }
        }
    }
}