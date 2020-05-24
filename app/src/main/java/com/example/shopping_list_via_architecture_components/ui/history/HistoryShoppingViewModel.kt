package com.example.shopping_list_via_architecture_components.ui.history

import android.app.Application
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.data.models.Product
import com.example.shopping_list_via_architecture_components.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HistoryShoppingViewModel (
    application: Application,
    private val repository: Repository
) : BaseViewModel(application) {
    private val disposables: CompositeDisposable = CompositeDisposable()
    val allPurchaseProducts = repository.allPurchaseProducts

    var productsAdapter = ProductsAdapter()

    fun setItems(products: List<Product>) {
        productsAdapter.setItems(products)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}