package com.example.tbcexercises.orderListScreen

import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.databinding.ItemOrderCardBinding
import com.example.tbcexercises.extensions.setTint
import com.example.tbcexercises.model.OrderStatus

class OrderListAdapter(val onOrderClick: (Order) -> Unit) :
    ListAdapter<Order, OrderListAdapter.OrderViewHolder>(OrderDiffUtil) {

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
        private val gestureDetector = GestureDetector(binding.root.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val order = getItem(adapterPosition)
                    onOrderClick(order)
                    return true
                }
            })

        fun onBind() {
            val order = getItem(adapterPosition)
            binding.apply {
                imgOrder.setImageResource(order.img)
                imgOrderColor.setTint(order.orderColor.value)
                txtOrderColor.text = order.orderColor.names

                txtOrderStatus.text = order.orderStatus.names
                btnOrderAction.text = order.orderStatus.buttonText

                txtOrderName.text = order.name
                txtOrderPrice.text =
                    root.context.getString(R.string.orderPrice, order.price.toString())

                txtQuantity.text =
                    root.context.getString(R.string.orderQuantity, order.quantity.toString())

                if (order.orderStatus == OrderStatus.COMPLETED) {
                    binding.root.setOnTouchListener { v, event ->
                        v.performClick()
                        gestureDetector.onTouchEvent(event) // Second Warning Can't Correct
                    }
                }

            }
        }
    }
}