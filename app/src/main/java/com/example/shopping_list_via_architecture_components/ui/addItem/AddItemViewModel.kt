package com.example.shopping_list_via_architecture_components.ui.addItem

import android.app.Application
import android.net.Uri
import com.example.shopping_list_via_architecture_components.data.Repository
import com.example.shopping_list_via_architecture_components.data.models.Product
import com.example.shopping_list_via_architecture_components.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddItemViewModel(
    application: Application,
    private val repository: Repository
) : BaseViewModel(application) {
    private var imageUri: Uri? = null


    fun saveItemProduct(nameProduct: String) {
        val product = Product(nameProduct)
        imageUri?.apply {
            repository.saveImageToFile(this)?.apply {
                product.imageUri = absolutePath
            }
        }

        launch(Dispatchers.IO + SupervisorJob()) {
            repository.saveItemProduct(product)
        }
    }

    fun saveImage(uri: Uri) {
        imageUri = uri
    }

    fun hasImage() = imageUri != null
}