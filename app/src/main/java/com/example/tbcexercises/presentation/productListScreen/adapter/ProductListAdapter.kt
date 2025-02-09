package com.example.tbcexercises.presentation.productListScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.data.imageLoader.GlideImageLoader
import com.example.tbcexercises.databinding.ItemProductBinding
import com.example.tbcexercises.domain.imageLoader.ImageLoader
import com.example.tbcexercises.domain.model.Product

class ProductListAdapter(val onClick: (Int) -> Unit, val imageLoader: GlideImageLoader) :
    ListAdapter<Product, ProductListAdapter.ProductListViewHolder>(ProductDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.onBind()
    }

    inner class ProductListViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val product = getItem(adapterPosition)

            binding.apply {
                txtProductPrice.text = product.price.toString()
                txtProductTitle.text = product.title
                txtProductCategory.text = product.category
                txtRatingCount.text = product.rating.count.toString()

                rbProductRating.rating = product.rating.rate.toFloat()

                imageLoader.loadImage(product.image, imgProductImage)

                root.setOnClickListener {
                    onClick(product.id)
                }
            }

        }
    }
}