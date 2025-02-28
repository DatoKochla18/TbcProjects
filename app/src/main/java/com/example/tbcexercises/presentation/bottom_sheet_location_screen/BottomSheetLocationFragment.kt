package com.example.tbcexercises.presentation.bottom_sheet_location_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentBottomSheetLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetLocationFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: BottomSheetLocationFragmentArgs by navArgs()

        binding.tvTitle.text = args.title
        binding.tvAddress.text = args.adress
        binding.tvCoordinates.text =
            getString(R.string.lat_lng, args.lat.toString(), args.lan.toString())

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
