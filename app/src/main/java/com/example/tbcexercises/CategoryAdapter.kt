package com.example.tbcexercises

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.CategoryCardBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition: Int = run {
        val idx = categories.indexOfFirst {
            it.name.lowercase() == "all"
        }
        if (idx == -1) 0 else idx // if category list dont have categori with all name it will return first one so first one will be default selected category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CategoryViewHolder(private val binding: CategoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val category = categories[adapterPosition]
            binding.imbCategory.apply {
                this.text = category.name
                this.setCompoundDrawablesWithIntrinsicBounds(category.image ?: 0, 0, 0, 0)
                this.isSelected = adapterPosition == selectedPosition

                this.setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition

                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    onCategoryClick(category.name)
                    Log.d("button Clicked", category.name)
                }
            }
        }
    }
}
