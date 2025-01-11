package com.example.tbcexercises.orderListScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.model.OrderStatus
import com.example.tbcexercises.databinding.FragmentOrderListBinding
import com.example.tbcexercises.orderReviewScreen.OrderReviewFragment
import com.example.tbcexercises.util.generateData

class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderType: String

    private val orderData = generateData()

    private val orderListAdapter by lazy {
        OrderListAdapter(onOrderClick = {
            showBottomSheetDialog(order = it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderType = arguments?.getString("type") ?: "Active"

        parentFragmentManager.setFragmentResultListener("updatedOrder", this) { _, bundle ->
            val order = bundle.getParcelable<Order>("newOrder")!!
            val idx = orderData.indexOfFirst { it.id == order.id }
            orderData[idx] = order
            orderListAdapter.submitList(filterData(type = orderType))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }
    private fun setUp(){
        binding.rvContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = orderListAdapter
        }

        orderListAdapter.submitList(filterData(type = orderType))
        Log.d("orderData",orderData.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterData(type: String): List<Order> {
        return if (type.lowercase() == "active") {
            orderData.filter { it.orderStatus == OrderStatus.ACTIVE }
        } else {
            orderData.filter { it.orderStatus == OrderStatus.COMPLETED }
        }
    }

    private fun showBottomSheetDialog(order: Order) {
        val orderReview = OrderReviewFragment().apply {
            arguments = bundleOf("order" to order)
        }
        orderReview.show(parentFragmentManager, OrderReviewFragment.TAG)
    }
}