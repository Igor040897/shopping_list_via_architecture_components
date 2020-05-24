package com.example.shopping_list_via_architecture_components.di.module

import com.example.shopping_list_via_architecture_components.data.FileWorker
import com.example.shopping_list_via_architecture_components.data.dataBase.DbStorageManager
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.data.RepositoryImpl
import com.example.shopping_list_via_architecture_components.data.dataBase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataBaseModule::class, FileWorkerModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(database: AppDataBase, fileWorker: FileWorker): Repository {
        val dbStorageManager = DbStorageManager.getInstance(database)
        return RepositoryImpl.getInstance(dbStorageManager, fileWorker)
    }
}