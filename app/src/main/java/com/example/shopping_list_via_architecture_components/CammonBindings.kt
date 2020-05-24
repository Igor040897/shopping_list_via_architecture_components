package com.example.shopping_list_via_architecture_components

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter(value = ["imageUri", "placeholder"], requireAll = false)
fun AppCompatImageView.setImage(imageUri: Uri?, placeholder: Drawable? = null) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_image_black_48dp)

    Glide.with(this)
        .load(imageUri)
        .apply(options)
        .into(this)
}