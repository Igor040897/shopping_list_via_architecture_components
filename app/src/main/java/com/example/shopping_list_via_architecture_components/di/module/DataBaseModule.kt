package com.example.shopping_list_via_architecture_components.di.module

import android.app.Application
import com.example.shopping_list_via_architecture_components.data.dataBase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDataBase {
        return AppDataBase.getInstance(application)
    }
}