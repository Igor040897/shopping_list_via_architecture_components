package com.example.shopping_list_via_architecture_components

import android.app.Application
import com.example.shopping_list_via_architecture_components.di.component.ApplicationComponent
import com.example.shopping_list_via_architecture_components.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}