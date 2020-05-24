package com.example.shopping_list_via_architecture_components.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    protected lateinit var binding: B

    abstract val contentLayoutId: Int
        @LayoutRes get

    /** Override this value to change toolbar title */
    protected open val title: Int? = null
        @StringRes get

    protected open fun setupBinding(binding: B) {}

    protected open fun setupViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        setupBinding(binding)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupToolbar()
    }

    private fun setupToolbar() {
        title?.run {
            activity?.toolbar?.title = getString(this)
        }
    }
}