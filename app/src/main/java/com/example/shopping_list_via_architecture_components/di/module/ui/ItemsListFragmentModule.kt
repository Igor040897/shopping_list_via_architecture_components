package com.example.shopping_list_via_architecture_components.di.module.ui

import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_via_architecture_components.di.module.RepositoryModule
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactory
import com.example.shopping_list_via_architecture_components.ui.itemsList.ItemListViewModel
import com.example.shopping_list_via_architecture_components.ui.itemsList.ItemsListFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [RepositoryModule::class])
abstract class ItemsListFragmentModule {
    @ContributesAndroidInjector(modules = [ItemListViewModelModule::class])
    abstract fun providesItemsListFragment(): ItemsListFragment
}

@Module
class ItemListViewModelModule {
    @Provides
    fun postAddItemViewModel(
        viewModelFactory: ViewModelFactory,
        fragment: ItemsListFragment
    ): ItemListViewModel {
        return ViewModelProvider(fragment, viewModelFactory)[ItemListViewModel::class.java]
    }
}