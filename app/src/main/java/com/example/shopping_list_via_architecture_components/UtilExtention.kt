package com.example.shopping_list_via_architecture_components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.shopping_list_via_architecture_components.data.IMAGE_EXTENSION
import java.io.File
import java.io.IOException

fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

@Throws(IOException::class)
fun Context.createImageFile(name: String): File {
    return File.createTempFile(
        name,
        IMAGE_EXTENSION,
        cacheDir
    )
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(this, Observer {
        callback.invoke(it)
    })
}


fun <T> LifecycleOwner.observeCommand(data: LiveData<T>, action: (T) -> Unit) {
    data.observe(this, Observer(action))
}

fun <T> LifecycleOwner.observe(data: LiveData<T>, action: (T) -> Unit) {
    observeCommand(data, action)
}

fun <T> Fragment.observeOnView(data: LiveData<T>, action: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer(action))
}

@MainThread
fun <T : Any> MutableLiveData<T>.call() {
    value = null
}

//fun <T : ViewModel> Fragment.obtainViewModel(clazz: Class<T>): T {
//    return activity?.run {
//        obtainViewModel(ViewModelFactoryTest.getInstance(application), clazz)
//    } ?: throw IllegalStateException("Activity is null when trying to obtain viewModel")
//}
//
//fun <VM : ViewModel> Fragment.obtainViewModel(factory: ViewModelProvider.Factory?, cls: Class<VM>): VM {
//    return ViewModelProviders.of(this, factory).get(cls)
//}