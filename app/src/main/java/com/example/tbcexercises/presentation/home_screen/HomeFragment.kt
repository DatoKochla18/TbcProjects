package com.example.tbcexercises.presentation.home_screen

import android.graphics.Bitmap
import android.os.Build
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLastState
import com.example.tbcexercises.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private var imgBitmap: Bitmap? = null
    private val viewModel by viewModels<HomeViewModel>()

    override fun start() {
        collectLastState(viewModel.uiState) { updateUiState(it) }
        collectLastState(viewModel.uiSideEffect) { getSideEffects(it) }
        listenToImageSource()
    }

    override fun listeners() {
        binding.btnAddPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pictureChooserFragment)
        }
        binding.btnUploadToFirebase.setOnClickListener {
            saveImgToFirebase()
        }
    }

    private fun updateUiState(uiState: HomeUiState) {
        binding.progressBar.root.isVisible = uiState.isLoading
    }

    private fun getSideEffects(homeUiSideEffect: HomeUiSideEffect) {
        when (homeUiSideEffect) {
            HomeUiSideEffect.FailedUpload -> binding.root.showSnackBar("Upload failed")
            HomeUiSideEffect.SuccessfulUpload -> binding.root.showSnackBar("Upload successful")
        }
    }

    private fun listenToImageSource() {
        parentFragmentManager.setFragmentResultListener(
            "imageRequest",
            viewLifecycleOwner
        ) { _, bundle ->
            val compressedBitmap = if (Build.VERSION.SDK_INT >= 33)
                bundle.getParcelable("image", Bitmap::class.java)
            else bundle.getParcelable("image")

            compressedBitmap?.let {
                imgBitmap = it
                binding.imgPhoto.setImageBitmap(it)
            }
        }
    }

    private fun saveImgToFirebase() {
        imgBitmap?.let { bitmap ->
            val file =
                File(requireContext().cacheDir, "uploaded_image_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            viewModel.onEvent(HomeUiEvent.SendImage(file))
        }
    }
}