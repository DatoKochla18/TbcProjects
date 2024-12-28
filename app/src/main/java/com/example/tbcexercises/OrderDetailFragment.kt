package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.tbcexercises.databinding.FragmentOrderDetailBinding
import com.example.tbcexercises.order.Order


class OrderDetailFragment : Fragment() {
    private var _order: Order? = null
    private val order get() = _order!!

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _order = it.getParcelable("order")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtOrderNumber.text = "#" + order.orderNumber.toString()
            txtTrackingNumber.text = order.id
            txtSubTotal.text = "$${order.subTotal}"
            txtDeliveryAddress.text = order.address

            binding.btnConfirmCanceled.visibility =
                if (order.status != PENDING) View.GONE else View.VISIBLE
            binding.btnConfirmDelivered.visibility =
                if (order.status != PENDING) View.GONE else View.VISIBLE
        }

        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()

        }
        binding.btnConfirmDelivered.setOnClickListener {
            val orderCopy = order.copy(status = DELIVERED)
            val bundle = bundleOf("orderChanged" to orderCopy)
            parentFragmentManager.setFragmentResult("order", bundle)
            parentFragmentManager.popBackStack()
        }
        binding.btnConfirmCanceled.setOnClickListener {
            val orderCopy = order.copy(status = CANCELLED)
            val bundle = bundleOf("orderChanged" to orderCopy)
            parentFragmentManager.setFragmentResult("order", bundle)
            parentFragmentManager.popBackStack()
        }
    }

    companion object {

        fun newInstance(param1: Order) =
            OrderDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("order", param1)
                }
            }
    }
}