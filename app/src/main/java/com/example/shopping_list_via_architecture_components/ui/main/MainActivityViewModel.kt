package com.example.shopping_list_via_architecture_components.ui.main

import android.app.Application
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel (
    application: Application,
    private val repository: Repository
) : BaseViewModel(application) {
    fun start() {

    }
}