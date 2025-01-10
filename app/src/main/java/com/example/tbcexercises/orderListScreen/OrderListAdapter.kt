package com.example.tbcexercises.orderListScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.databinding.ItemOrderCardBinding
import com.example.tbcexercises.extensions.setTint

class OrderListAdapter : ListAdapter<Order, OrderListAdapter.OrderViewHolder>(OrderDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            ItemOrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.onBind()
    }


    inner class OrderViewHolder(private val binding: ItemOrderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val order = getItem(adapterPosition)
            binding.apply {
                imgOrder.setImageResource(order.img)
                imgOrderColor.setTint(order.orderColor.value)
                txtOrderColor.text = order.orderColor.names

                txtOrderStatus.text = order.orderStatus.names
                btnOrderAction.text = order.orderStatus.buttonText

                txtOrderName.text = order.name
                txtOrderPrice.text = "$"+ order.price.toString()

                txtQuantity.text = order.quantity.toString()
            }
        }
    }
}