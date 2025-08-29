package com.priyanshparekh.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.priyanshparekh.core.model.home.SearchResultResponse
import com.priyanshparekh.core.network.Status
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val homeRepository = HomeRepository()


    private val _searchStatus = MutableLiveData<Status<SearchResultResponse>>()
    val searchStatus: LiveData<Status<SearchResultResponse>> = _searchStatus

    fun getSearchResults(query: String) {
        viewModelScope.launch {
            val response = homeRepository.getSearchResults(query)
            val error = response.errorBody()?.string()
            val code = response.code()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    _searchStatus.value = Status.SUCCESS(body)
                } else {
                    _searchStatus.value = Status.ERROR("Error: $error; Code: $code")
                }
            } else {
                _searchStatus.value = Status.ERROR("Error: $error; Code: $code")
            }
        }
    }

}