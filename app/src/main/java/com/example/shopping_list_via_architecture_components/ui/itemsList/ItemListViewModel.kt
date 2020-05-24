package com.example.shopping_list_via_architecture_components.ui.itemsList

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.shopping_list_via_architecture_components.call
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.data.models.Product
import com.example.shopping_list_via_architecture_components.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemListViewModel (
    application: Application,
    private val repository: Repository
) : BaseViewModel(application) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    var selectModeProductsAdapter: SelectModeProductsAdapter =
        SelectModeProductsAdapter()

    val longClickItemCommand = MutableLiveData<Unit>()
    val selectedItemCommand = MutableLiveData<Int>()

    val allNotPurchaseProducts = repository.allNotPurchaseProducts

    init {
        selectModeProductsAdapter.onPurchaseClickListener =
            object : SelectModeProductsAdapter.OnPurchaseItemClickListener {
                override fun onLongClicked() {
                    longClickItemCommand.call()
                }

                override fun onClicked(size: Int) {
                    selectedItemCommand.postValue(size)
                }

            }
    }

    fun setItems(products: List<Product>) {
        selectModeProductsAdapter.setItems(products)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun clearItemSelections() {
        selectModeProductsAdapter.clearSelections()
    }

    fun shopSelectItems() {
        val selectedItems = selectModeProductsAdapter.getSelectedItems()

        launch(Dispatchers.IO + SupervisorJob()) {
            repository.saveItemsProduct(selectedItems)
        }
    }

    fun selectAll() = selectModeProductsAdapter.selectAll()
}