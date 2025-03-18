package com.example.tbcexercises.presentation.screen.search.category_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemCategoryBinding
import com.example.tbcexercises.presentation.model.Category
import com.example.tbcexercises.presentation.screen.search.balls_adapter.Ball
import com.example.tbcexercises.presentation.screen.search.balls_adapter.BallAdapter

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind()
    }


    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val category = getItem(adapterPosition)

            binding.txtCategoryName.text = category.name

            val ballAdapter = BallAdapter()

            binding.rvBalls.apply {
                adapter = ballAdapter
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }

            val balls = if (category.depth == 0) listOf() else (0..<category.depth).map { Ball(it) }

            ballAdapter.submitList(balls.toList())
        }
    }
}