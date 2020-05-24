package com.example.shopping_list_via_architecture_components.data.dataBase

import com.example.shopping_list_via_architecture_components.data.models.Product

class DbStorageManager private constructor(
    private val appDataBase: AppDataBase
) {

    fun saveItemProduct(product: Product) {
        appDataBase.productDao().insertProduct(product)
    }

    fun saveItemsProduct(products: List<Product>) {
        appDataBase.productDao().insertProducts(products)
    }

    fun getAllPurchaseProducts() = appDataBase.productDao().getAllPurchaseProducts()

    fun getAllNotPurchaseProducts() = appDataBase.productDao().getAllNotPurchaseProducts()

    companion object {

        @Volatile
        private var INSTANCE: DbStorageManager? = null

        fun getInstance(appDataBase: AppDataBase): DbStorageManager {
            return INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: DbStorageManager(
                        appDataBase
                    ).also { INSTANCE = it }
            }
        }
    }
}