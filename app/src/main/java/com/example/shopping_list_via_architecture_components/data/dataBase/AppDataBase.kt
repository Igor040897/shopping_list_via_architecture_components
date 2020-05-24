package com.example.shopping_list_via_architecture_components.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopping_list_via_architecture_components.data.models.Product

const val NAME_DATABASE = "database-app"

const val VERSION_DATABASE_1 = 1
const val CURRENT_VERSION_DATABASE = VERSION_DATABASE_1

@Database(
    entities = [Product::class],
    version = CURRENT_VERSION_DATABASE,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(
            context: Context
        ): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room
                    .databaseBuilder(context, AppDataBase::class.java, NAME_DATABASE)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}