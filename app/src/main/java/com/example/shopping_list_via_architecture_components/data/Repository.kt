package com.example.shopping_list_via_architecture_components.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.shopping_list_via_architecture_components.data.models.Product
import io.reactivex.Flowable
import java.io.File

interface Repository {

    val allPurchaseProducts: LiveData<List<Product>>

    val allNotPurchaseProducts: LiveData<List<Product>>

    fun saveItemProduct(product: Product)

    fun saveItemsProduct(products: List<Product>)

    fun getTempImageFileUri(name: String): Uri?

    fun saveImageToFile(uri: Uri): File?

}