package com.example.tbcexercises.orderReviewScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentOrderReviewBinding
import com.example.tbcexercises.extensions.setTint
import com.example.tbcexercises.model.Order
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderReviewFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentOrderReviewBinding? = null
    private val binding get() = _binding!!

    private var order: Order? = null

    companion object {
        const val TAG = "OrderReviewBottomSheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        order = arguments?.getParcelable("order")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderReviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.apply {
            imgOrder.setImageResource(order!!.img)
            imgOrderColor.setTint(order!!.orderColor.value)
            txtOrderColor.text = order!!.orderColor.names

            txtOrderStatus.text = order!!.orderStatus.names
            txtOrderName.text = order!!.name
            txtOrderPrice.text = "$" + order!!.price.toString()

            txtQuantity.text = "Qty = " + order!!.quantity.toString()

        }

        binding.btnSubmit.setOnClickListener {
            val reviewText = binding.etReviewAnswer.text.toString()
            val reviewRating = binding.rbRatings.rating.toInt()

            val newOrder = order!!.copy(ratingText = reviewText, ratingStars = reviewRating)

            setFragmentResult("updatedOrder", bundleOf("newOrder" to newOrder))
            Log.d("updatedOrder", newOrder.toString())
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}