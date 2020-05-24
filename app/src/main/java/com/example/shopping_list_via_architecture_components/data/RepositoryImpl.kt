package com.example.shopping_list_via_architecture_components.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.shopping_list_via_architecture_components.data.dataBase.DbStorageManager
import com.example.shopping_list_via_architecture_components.data.models.Product
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.coroutines.CoroutineContext

class RepositoryImpl(
    private val dbStorageManager: DbStorageManager,
    private val fileWorker: FileWorker
) : Repository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override val allPurchaseProducts: LiveData<List<Product>>
        get() = dbStorageManager.getAllPurchaseProducts()

    override val allNotPurchaseProducts: LiveData<List<Product>>
        get() = dbStorageManager.getAllNotPurchaseProducts()

    override fun saveItemProduct(product: Product) {
        dbStorageManager.saveItemProduct(product)
    }

    override fun saveItemsProduct(products: List<Product>) {
        launch(Dispatchers.IO + SupervisorJob()) {
            dbStorageManager.saveItemsProduct(products)
        }
    }

    override fun getTempImageFileUri(name: String) = fileWorker.getTempImageFileUri(name)

    override fun saveImageToFile(uri: Uri): File? =
        fileWorker.saveImage(uri, Date().toString())?.let {
            val file = File(it)
            if (file.exists()) {
                file
            } else null
        }

    companion object {
        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(
            dbStorageManager: DbStorageManager,
            fileWorker: FileWorker
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepositoryImpl(dbStorageManager, fileWorker).also { INSTANCE = it }
            }
        }
    }
}