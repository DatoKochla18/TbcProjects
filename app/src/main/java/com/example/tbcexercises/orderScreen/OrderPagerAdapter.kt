package com.example.tbcexercises.orderScreen

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tbcexercises.model.OrderStatus
import com.example.tbcexercises.orderListScreen.OrderListFragment

class OrderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 -> filterFragment(orderType = OrderStatus.COMPLETED)
            else -> filterFragment(orderType = OrderStatus.ACTIVE)
        }

    }

    private fun filterFragment(orderType: OrderStatus): Fragment {
        val args = bundleOf("type" to orderType)
        val orderListFragment = OrderListFragment().apply {
            arguments = args
        }
        return orderListFragment
    }
}