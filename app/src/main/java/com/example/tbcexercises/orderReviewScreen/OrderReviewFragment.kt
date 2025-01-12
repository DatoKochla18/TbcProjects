package com.example.tbcexercises.orderReviewScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentOrderReviewBinding
import com.example.tbcexercises.extensions.setTint
import com.example.tbcexercises.model.Order
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderReviewFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentOrderReviewBinding? = null
    private val binding get() = _binding!!

    private val args: OrderReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetSetUp()
        viewSetUp()
        listeners()
    }

    private fun viewSetUp() {
        binding.apply {
            imgOrder.setImageResource(args.order.img)

            imgOrderColor.setTint(args.order.orderColor.value)
            txtOrderColor.text = args.order.orderColor.names

            txtOrderStatus.text = args.order.orderStatus.names
            txtOrderName.text = args.order.name

            txtOrderPrice.text = getString(R.string.orderPrice, args.order.price.toString())
            txtQuantity.text = getString(R.string.orderQuantity, args.order.quantity.toString())
        }
    }

    private fun bottomSheetSetUp() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun listeners() {
        binding.apply {
            btnSubmit.setOnClickListener {
                sendOrder()

            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun sendOrder() {
        val reviewText = binding.etReviewAnswer.text.toString()
        val reviewRating = binding.rbRatings.rating.toInt()

        val newOrder = args.order.copy(ratingText = reviewText, ratingStars = reviewRating)

        setFragmentResult("updatedOrder", bundleOf("newOrder" to newOrder))
        Log.d("updatedOrder", newOrder.toString())
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}