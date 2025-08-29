package com.priyanshparekh.plated.startup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.priyanshparekh.core.model.MessageResponse
import com.priyanshparekh.core.model.ingredient.AddIngredientsRequest
import com.priyanshparekh.core.model.ingredient.Ingredient
import com.priyanshparekh.core.model.ingredient.IngredientsApiResponse
import com.priyanshparekh.core.network.RetrofitInstance
import com.priyanshparekh.core.network.Status
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SplashViewModel: ViewModel() {

    private val _fetchStatus = MutableLiveData<Status<List<Ingredient>>>()
    val fetchStatus: LiveData<Status<List<Ingredient>>> = _fetchStatus

    fun fetchIngredients() {
        viewModelScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://www.themealdb.com/api/json/v1/1/list.php?i=list")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()

                    _fetchStatus.postValue(Status.ERROR("Error: ${e.message}"))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {
                            val gson = Gson()
                            val result = gson.fromJson(body.string(), IngredientsApiResponse::class.java)
                            _fetchStatus.postValue(Status.SUCCESS(result.meals))
                        } else {
                            _fetchStatus.postValue(Status.ERROR("Error: ${response.message()}"))
                        }
                    } else {
                        _fetchStatus.postValue(Status.ERROR("Error: ${response.message()}"))
                    }
                }

            })
        }
    }

    private val _uploadStatus = MutableLiveData<Status<MessageResponse>>()
    val uploadStatus: LiveData<Status<MessageResponse>> = _uploadStatus

    fun uploadIngredients(addIngredientsRequest: AddIngredientsRequest) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.uploadIngredients(addIngredientsRequest)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _uploadStatus.value = Status.SUCCESS(body)
                } else {
                    Log.d("TAG", "splashViewModel: uploadIngredients: inner if")
                    _uploadStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
                }
            } else {
                Log.d("TAG", "splashViewModel: uploadIngredients: outer if: response code: ${response.code()}")
                Log.d("TAG", "splashViewModel: uploadIngredients: outer if: response message: ${response.message()}")
                _uploadStatus.value = Status.ERROR("Error: ${response.errorBody()?.string()}")
            }
        }

    }
}

