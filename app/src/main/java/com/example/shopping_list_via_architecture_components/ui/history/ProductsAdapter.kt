package com.example.shopping_list_via_architecture_components.ui.history

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_list_via_architecture_components.R
import com.example.shopping_list_via_architecture_components.data.models.Product
import com.example.shopping_list_via_architecture_components.databinding.ItemProductBinding
import com.example.shopping_list_via_architecture_components.inflateView
import com.example.shopping_list_via_architecture_components.setImage
import com.example.shopping_list_via_architecture_components.ui.itemsList.ProductsDiffCallback
import java.io.File

private const val TYPE_IMAGE = 1
private const val TYPE_TEXT = 2
private const val TYPE_MIX = 3

open class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductItemVH>() {

    protected var items: ArrayList<Product> = ArrayList()

    fun setItems(products: List<Product>) {
        val diffCallback =
            ProductsDiffCallback(
                items,
                products
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(products)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemVH {
        val binding = ItemProductBinding.bind(parent.inflateView(R.layout.item_product))
        return ProductItemVH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductItemVH, position: Int) {
        val productItem = items[position]
        holder.bind(productItem)
    }

    override fun getItemViewType(position: Int) = items[position].let {
        if (it.imageUri != null && it.name.isNotEmpty()) {
            TYPE_MIX
        } else if (it.imageUri != null) {
            TYPE_IMAGE
        } else {
            TYPE_TEXT
        }
    }

    open inner class ProductItemVH(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: Product) {
            when (getItemViewType(adapterPosition)) {
                TYPE_IMAGE -> {
                    bindImageType(item)
                }
                TYPE_TEXT -> {
                    bindTextType(item)
                }
                TYPE_MIX -> {
                    bindMixType(item)
                }
            }
        }

        private fun bindImageType(item: Product) {
            binding.apply {
                photoImageView.visibility = View.VISIBLE
                photoImageView.setImage(Uri.fromFile(File(item.imageUri)))
                nameTextView.visibility = View.GONE
            }
        }

        private fun bindTextType(item: Product) {
            binding.apply {
                nameTextView.visibility = View.VISIBLE
                nameTextView.text = item.name
                photoImageView.visibility = View.GONE
            }
        }

        private fun bindMixType(item: Product) {
            binding.apply {
                photoImageView.visibility = View.VISIBLE
                nameTextView.visibility = View.VISIBLE
                photoImageView.setImage(Uri.fromFile(File(item.imageUri)))
                nameTextView.text = item.name
            }
        }
    }
}