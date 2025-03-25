package com.example.tbcexercises.presentation.home_screen

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun start() {
        parentFragmentManager.setFragmentResultListener(
            "imageRequest",
            viewLifecycleOwner
        ) { _, bundle ->
            val compressedBitmap = if (Build.VERSION.SDK_INT >= 33) bundle.getParcelable(
                "image",
                Bitmap::class.java
            ) else bundle.getParcelable("image")

            compressedBitmap?.let {
                binding.imgPhoto.setImageBitmap(it)
            }
        }
    }

    override fun listeners() {
        binding.btnAddPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pictureChooserFragment)

        }
    }

}