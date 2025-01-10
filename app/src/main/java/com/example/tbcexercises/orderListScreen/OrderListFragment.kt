package com.example.tbcexercises.orderListScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.model.OrderStatus
import com.example.tbcexercises.databinding.FragmentOrderListBinding
import com.example.tbcexercises.util.generateData

class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderType: String

    private val orderData = generateData()

    private val orderListAdapter by lazy {
        OrderListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderType = arguments?.getString("type") ?: "Active"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvContainer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvContainer.adapter = orderListAdapter

        orderListAdapter.submitList(filterData(type = orderType))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterData(type: String): List<Order> {
        if (type.lowercase() == "active") {
            return orderData.filter { it.orderStatus == OrderStatus.ACTIVE }
        } else {
            return orderData.filter { it.orderStatus == OrderStatus.COMPLETED }
        }
    }
}