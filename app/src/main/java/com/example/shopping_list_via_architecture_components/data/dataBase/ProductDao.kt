package com.example.shopping_list_via_architecture_components.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopping_list_via_architecture_components.data.models.Product
import io.reactivex.Flowable

@Dao
abstract class ProductDao {

    @Query("SELECT * FROM Product WHERE isPurchase == 1 ")
    abstract fun getAllPurchaseProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE isPurchase == 0")
    abstract fun getAllNotPurchaseProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProducts(product: List<Product>)
}