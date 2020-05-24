package com.example.shopping_list_via_architecture_components.di.module.ui

import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_via_architecture_components.di.module.RepositoryModule
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactory
import com.example.shopping_list_via_architecture_components.ui.history.HistoryShoppingFragment
import com.example.shopping_list_via_architecture_components.ui.history.HistoryShoppingViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [RepositoryModule::class])
abstract class HistoryShoppingFragmentModule {
    @ContributesAndroidInjector(modules = [HistoryShoppingViewModelModule::class])
    abstract fun providesHistoryShoppingFragment(): HistoryShoppingFragment
}

@Module
class HistoryShoppingViewModelModule {
    @Provides
    fun postAddItemViewModel(
        viewModelFactory: ViewModelFactory,
        fragment: HistoryShoppingFragment
    ): HistoryShoppingViewModel {
        return ViewModelProvider(fragment, viewModelFactory)[HistoryShoppingViewModel::class.java]
    }
}