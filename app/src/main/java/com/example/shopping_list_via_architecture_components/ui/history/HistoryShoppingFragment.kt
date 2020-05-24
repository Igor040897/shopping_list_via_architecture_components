package com.example.shopping_list_via_architecture_components.ui.history

import android.content.Context
import com.example.shopping_list_via_architecture_components.R
import com.example.shopping_list_via_architecture_components.databinding.FragmentHistoryShoppingBinding
import com.example.shopping_list_via_architecture_components.observe
import com.example.shopping_list_via_architecture_components.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HistoryShoppingFragment : BaseFragment<FragmentHistoryShoppingBinding>() {

    companion object {
        fun newInstance() = HistoryShoppingFragment()
    }

    override val contentLayoutId = R.layout.fragment_history_shopping
    override var title = R.string.label_history_shopping

    @Inject
    lateinit var viewModel: HistoryShoppingViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setupBinding(binding: FragmentHistoryShoppingBinding) {
        super.setupBinding(binding)
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.apply {
            observe(allPurchaseProducts) {
                setItems(it)
            }
        }
    }
}