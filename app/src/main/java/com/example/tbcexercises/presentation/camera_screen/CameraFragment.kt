package com.example.tbcexercises.presentation.camera_screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentCameraBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.compressBitmap
import com.example.tbcexercises.presentation.extension.rotateImageIfRequired
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File


class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    private var imageCapture: ImageCapture? = null
    private var currentLensFacing = CameraSelector.DEFAULT_BACK_CAMERA

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            showPermissionDeniedDialog()
        }
    }

    override fun start() {
        checkCameraPermissions()
    }

    override fun listeners() {
        binding.btnTake.setOnClickListener {
            takePhoto()
        }
        binding.btnSwitchCamera.setOnClickListener {
            changeCameraOrientation()
        }
    }

    private fun changeCameraOrientation() {
        currentLensFacing = if (currentLensFacing == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }

        startCamera()
    }

    private fun checkCameraPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showPermissionRationaleDialog()
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.camera_permission_required))
            .setMessage(getString(R.string.this_app_needs_access_to_your_camera_to_take_photos_please_grant_camera_permission))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }.setOnCancelListener {
                findNavController().popBackStack()
            }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.camera_permission_required))
            .setMessage(getString(R.string.camera_access_is_required_to_use_this_feature_please_enable_camera_permissions_in_app_settings))
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }.setOnCancelListener {
                findNavController().popBackStack()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireContext().packageName}")
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = binding.cameraPreview.surfaceProvider
                }
                imageCapture = ImageCapture.Builder().build()

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    currentLensFacing,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.d("HOMEFRAGMENT", exc.toString())
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        binding.apply {
            btnTake.isEnabled = false
            btnSwitchCamera.isEnabled = false

            cameraPreview.bitmap?.let { bitmap ->
                imgSnapshot.setImageBitmap(bitmap)
                imgSnapshot.isVisible = true
            }
        }


        val photoFile = File.createTempFile("JPEG_", ".jpg", requireContext().cacheDir)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    binding.apply {
                        imgSnapshot.isVisible = false
                        btnTake.isEnabled = true
                        btnSwitchCamera.isEnabled = true
                    }


                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val originalBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

                    val correctedBitmap =
                        originalBitmap.rotateImageIfRequired(photoFile.absolutePath)
                    setFragmentResult(
                        "imageRequest",
                        bundleOf("image" to correctedBitmap.compressBitmap())
                    )
                    findNavController().popBackStack()
                }
            }
        )
    }

}
