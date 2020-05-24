package com.example.shopping_list_via_architecture_components.di.module.ui

import androidx.lifecycle.ViewModelProvider
import com.example.shopping_list_via_architecture_components.ui.addItem.addPhoto.AddPhotoDialogFragment
import com.example.shopping_list_via_architecture_components.ui.addItem.addPhoto.AddPhotoViewModel
import com.example.shopping_list_via_architecture_components.di.module.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddPhotoDialogFragmentModule {
    @ContributesAndroidInjector(modules = [AddPhotoViewModelModule::class])
    abstract fun providesAddPhotoDialogFragment(): AddPhotoDialogFragment
}

@Module
class AddPhotoViewModelModule {
    @Provides
    fun postAddPhotoViewModel(
        viewModelFactory: ViewModelFactory,
        fragment: AddPhotoDialogFragment
    ): AddPhotoViewModel {
        return ViewModelProvider(fragment, viewModelFactory)[AddPhotoViewModel::class.java]
    }
}