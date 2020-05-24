package com.example.shopping_list_via_architecture_components.ui.itemsList

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.keyIterator
import com.example.shopping_list_via_architecture_components.data.models.Product
import com.example.shopping_list_via_architecture_components.databinding.ItemProductBinding
import com.example.shopping_list_via_architecture_components.ui.history.ProductsAdapter

class SelectModeProductsAdapter : ProductsAdapter() {

    private val selectedItems = SparseBooleanArray()
    private var selectMode = false

    lateinit var onPurchaseClickListener: OnPurchaseItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectModeProductItemVH {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectModeProductItemVH(binding)
    }

    override fun getItemCount() = items.size

    inner class SelectModeProductItemVH(private val binding: ItemProductBinding) :
        ProductsAdapter.ProductItemVH(binding) {

        init {
            binding.root.setOnClickListener {
                if (selectMode) {
                    click()
                    onPurchaseClickListener.onClicked(selectedItems.size())
                }
            }

            binding.root.setOnLongClickListener {
                selectMode = true
                click()
                onPurchaseClickListener.onLongClicked()
                onPurchaseClickListener.onClicked(selectedItems.size())
                true
            }
        }

        private fun click() {
            if (selectedItems[adapterPosition, false]) {
                selectedItems.delete(adapterPosition)
            } else {
                selectedItems.put(adapterPosition, true)
            }
            notifyItemChanged(adapterPosition)
        }

        override fun bind(item: Product) {
            super.bind(item)
            selectedItems[adapterPosition, false].apply {
                if (this) {
                    binding.checkImageView.visibility = View.VISIBLE
                    binding.photoImageView.alpha = 0.5f
                    binding.nameTextView.alpha = 0.5f
                    binding.root.alpha = 0.5f
                } else {
                    binding.checkImageView.visibility = View.GONE
                    binding.photoImageView.alpha = 1f
                    binding.nameTextView.alpha = 1f
                    binding.root.alpha = 1f
                }
            }
        }
    }

    fun selectAll(): Int {
        items.indices.forEach {
            selectedItems.put(it, true)
        }
        notifyDataSetChanged()
        return selectedItems.size()
    }

    fun clearSelections() {
        selectMode = false
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Product> {
        val clone = items.clone() as ArrayList<Product>
        selectedItems.keyIterator().forEach { i: Int ->
            clone[i].isPurchase = true
        }
        return clone
    }

    interface OnPurchaseItemClickListener {
        fun onLongClicked()
        fun onClicked(size: Int)
    }
}