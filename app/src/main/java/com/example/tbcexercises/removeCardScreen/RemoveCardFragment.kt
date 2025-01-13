package com.example.tbcexercises.removeCardScreen

import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.databinding.FragmentRemoveCardBinding
import com.example.tbcexercises.utils.BaseBottomSheetFragment


class RemoveCardFragment :
    BaseBottomSheetFragment<FragmentRemoveCardBinding>(FragmentRemoveCardBinding::inflate) {

    private val args: RemoveCardFragmentArgs by navArgs()

    override fun start() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }

        binding.btnYes.setOnClickListener {
            val uuid = bundleOf("oldCardId" to args.card.id)
            parentFragmentManager.setFragmentResult("removeCard", uuid)
            dismiss()
        }
    }


}