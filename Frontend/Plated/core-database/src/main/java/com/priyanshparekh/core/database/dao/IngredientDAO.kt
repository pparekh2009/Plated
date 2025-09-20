package com.priyanshparekh.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.priyanshparekh.core.database.entity.Ingredient

@Dao
interface IngredientDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: List<Ingredient>)

    @Query("SELECT * FROM ingredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @Query("SELECT COUNT(*) FROM ingredients")
    suspend fun count(): Int
}