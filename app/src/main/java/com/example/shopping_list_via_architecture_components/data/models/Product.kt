package com.example.shopping_list_via_architecture_components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    var name : String,
    var imageUri : String? = null,
    var isPurchase: Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id: Long? = null
}