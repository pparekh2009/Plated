package com.priyanshparekh.feature.home

import com.priyanshparekh.core.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository {

    suspend fun getSearchResults(query: String) = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getSearchResults(query)
    }

    suspend fun getTrendingRecipes() = withContext(Dispatchers.IO) {
        return@withContext RetrofitInstance.api.getTrendingRecipes()
    }

}