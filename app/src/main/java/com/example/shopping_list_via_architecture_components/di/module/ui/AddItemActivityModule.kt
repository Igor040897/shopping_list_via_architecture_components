package com.example.shopping_list_via_architecture_components.di.module.ui

import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_via_architecture_components.ui.addItem.AddItemActivity
import com.example.shopping_list_via_architecture_components.ui.addItem.AddItemViewModel
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddItemActivityModule {
    @ContributesAndroidInjector(modules = [AddItemViewModelModule::class])
    abstract fun providesAddItemActivity(): AddItemActivity
}

@Module
class AddItemViewModelModule {
    @Provides
    fun postAddItemViewModel(
        viewModelFactory: ViewModelFactory,
        activity: AddItemActivity
        ): AddItemViewModel {
        return ViewModelProvider(activity, viewModelFactory)[AddItemViewModel::class.java]
    }
}