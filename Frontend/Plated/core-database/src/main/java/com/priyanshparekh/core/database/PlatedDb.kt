package com.priyanshparekh.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.priyanshparekh.core.database.dao.IngredientDAO
import com.priyanshparekh.core.database.entity.Ingredient

@Database(entities = [Ingredient::class], version = 1, exportSchema = false)
abstract class PlatedDb: RoomDatabase() {

    abstract fun ingredientDAO(): IngredientDAO

    companion object {
        @Volatile
        private var INSTANCE: PlatedDb? = null
        fun getDatabase(context: Context): PlatedDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlatedDb::class.java,
                    "plated_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}