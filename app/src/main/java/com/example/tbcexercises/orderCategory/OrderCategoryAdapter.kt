package com.example.tbcexercises.orderCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbcexercises.databinding.OrderStatusItemBinding

class OrderCategoryAdapter(val filter: (OrderStatus) -> Unit) :
    ListAdapter<OrderStatus, OrderCategoryAdapter.OrderCategoryViewHolder>(orderStatusDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCategoryViewHolder {
        val binding =
            OrderStatusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderCategoryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class OrderCategoryViewHolder(val binding: OrderStatusItemBinding) :
        ViewHolder(binding.root) {
        fun onBind(orderStatus: OrderStatus) {
            binding.btnOrder.apply {
                text = orderStatus.name
                isSelected = orderStatus.isSelected



                setOnClickListener {
                    val currentList = currentList.map {
                        it.copy(isSelected = it.id == orderStatus.id)
                    }
                    submitList(currentList)

                }
                if (isSelected){
                    filter(orderStatus)
                }
            }
        }

    }

}