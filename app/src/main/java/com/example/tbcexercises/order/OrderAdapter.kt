package com.example.tbcexercises.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.OrderItemBinding
import com.example.tbcexercises.Extensions.toDate

class OrderAdapter(val goToDetail: (Order) -> Unit) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(orderDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class OrderViewHolder(val binding: OrderItemBinding) : ViewHolder(binding.root) {
        fun onBind(order: Order) {
            binding.apply {
                txtOrderNumber.text = "#" + order.orderNumber.toString()
                txtTrackingNumber.text = order.id
                txtDate.text = order.date.toDate()
                txtQuantity.text = order.quantity.toString()
                txtSubTotal.text = "$${order.subTotal}"
                txtOrderStatus.text = order.status.name
                val resolvedColor =
                    binding.root.context.getColor(order.status.color ?: R.color.black)
                txtOrderStatus.setTextColor(resolvedColor)

                btnDetails.setOnClickListener {
                    goToDetail(order)
                }
            }
        }
    }

}