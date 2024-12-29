package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentOrderListBinding
import com.example.tbcexercises.order.Order
import com.example.tbcexercises.order.OrderAdapter
import com.example.tbcexercises.orderCategory.OrderCategoryAdapter
import com.example.tbcexercises.orderCategory.OrderStatus

class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private val orderData = generateOrderData()

    private var orderCategoryData: MutableList<OrderStatus> = generateOrderStatusData()

    private var firstLoad = true
    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(goToDetail = { order -> launchOrderDetail(order) })
    }

    private val orderCategoryAdapter: OrderCategoryAdapter by lazy {
        OrderCategoryAdapter(filter = { category ->
            filterOrderData(category)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("order", this) { _, bundle ->
            val order = bundle.getParcelable<Order>("orderChanged")!!
            updateOrder(order)
        }
        savedInstanceState?.let {
            firstLoad = it.getBoolean("firstLoad")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUp() {


        binding.apply {
            rvOrders.layoutManager = LinearLayoutManager(requireContext())
            rvOrders.adapter = orderAdapter

            rvButtonStatus.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvButtonStatus.adapter = orderCategoryAdapter
        }
        orderAdapter.submitList(orderData)


        if (firstLoad) {
            orderCategoryAdapter.submitList(orderCategoryData)
            firstLoad = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("firstLoad", firstLoad)
    }

    private fun filterOrderData(orderStatus: OrderStatus) {
        val result = orderData.filter { it.status.name.lowercase() == orderStatus.name.lowercase() }
        orderAdapter.submitList(result.toList())

    }

    private fun updateOrder(order: Order) {
        val idx = orderData.indexOfFirst { it.id == order.id }
        orderData[idx] = order
        orderAdapter.submitList(orderData.toList())
    }

    private fun launchOrderDetail(order: Order) {
        parentFragmentManager.beginTransaction().run {
            replace(
                R.id.frContainer, OrderDetailFragment.newInstance(order)
            )
            addToBackStack(null)
            commit()
        }
    }

}