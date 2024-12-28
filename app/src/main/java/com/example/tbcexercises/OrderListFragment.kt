package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private val orderData = generateOrderData()

    private val orderCategoryData = generateOrderStatusData()

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }
    private val orderCategoryAdapter: OrderCategoryAdapter by lazy {
        OrderCategoryAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrders.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = orderAdapter

        orderAdapter.submitList(orderData)

        Log.d("orderData", orderData.toString())

        binding.rvButtonStatus.layoutManager =
            LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvButtonStatus.adapter = orderCategoryAdapter
        orderCategoryAdapter.submitList(orderCategoryData)
    }


}