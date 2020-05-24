package com.example.shopping_list_via_architecture_components.di.component

import android.app.Application
import com.example.shopping_list_via_architecture_components.App
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.di.module.DataBaseModule
import com.example.shopping_list_via_architecture_components.di.module.FileWorkerModule
import com.example.shopping_list_via_architecture_components.di.module.RepositoryModule
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactoryModule
import com.example.shopping_list_via_architecture_components.di.module.ui.*
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        DataBaseModule::class,
        FileWorkerModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        MainActivityModule::class,
        AddItemActivityModule::class,
        ItemsListFragmentModule::class,
        HistoryShoppingFragmentModule::class,
        AddPhotoDialogFragmentModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: App)
}