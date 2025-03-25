package com.example.tbcexercises.presentation.bottom_sheet_camera

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentPictureChooserBinding
import com.example.tbcexercises.presentation.extension.compressBitmap
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PictureChooserFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPictureChooserBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(it)
                bitmap?.let { bmp ->
                    setFragmentResult("imageRequest", bundleOf("image" to bmp.compressBitmap()))
                    dismiss()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPictureChooserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTakePhoto.setOnClickListener {
            findNavController().navigate(R.id.action_pictureChooserFragment_to_cameraFragment)
            dismiss()
        }

        binding.btnFromGallery.setOnClickListener {
            galleryLauncher.launch(GALLERY_MIMETYPE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        }
    }

    companion object {
        const val GALLERY_MIMETYPE = "image/*"
    }
}