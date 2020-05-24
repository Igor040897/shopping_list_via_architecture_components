package com.example.shopping_list_via_architecture_components.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: B

    abstract val contentLayoutId: Int
        @LayoutRes get

    abstract fun setupBinding(binding: B)

    protected open fun setupViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        setupBinding(binding)
        setupViewModel()
    }
}