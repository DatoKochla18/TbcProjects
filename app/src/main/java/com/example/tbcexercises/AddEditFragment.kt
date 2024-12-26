package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.tbcexercises.databinding.FragmentAddEditBinding


class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()

    }

    private fun setUp() {

        val oldAddress = arguments?.getParcelable<Address>(OLD_ADDRESS_KEY)
        binding.etAddressTitle.setText(oldAddress?.title ?: "")
        binding.etDetailedLocation.setText(oldAddress?.detailedLocation ?: "")
        listeners(oldAddress)
    }

    private fun listeners(oldAddress: Address?) {
        binding.btnConfirm.setOnClickListener {
            oldAddress?.let {
                editAddress(it)
            } ?: addAddress()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addAddress() {
        val addressTitle = binding.etAddressTitle.text.toString()
        val addressDetailedLocation = binding.etDetailedLocation.text.toString()
        val img = R.drawable.office

        if (addressTitle.isEmpty() || addressDetailedLocation.isEmpty()) {
            Toast.makeText(requireContext(), "Fields should not be empty", Toast.LENGTH_SHORT)
                .show()
        } else {

            val address = Address(
                id = System.currentTimeMillis().toInt(),
                img = img,
                title = addressTitle,
                detailedLocation = addressDetailedLocation
            )

            val result = bundleOf(NEW_ADDRESS_KEY to address)
            parentFragmentManager.setFragmentResult(NEW_ADDRESS_REQUEST_KEY, result)

            parentFragmentManager.popBackStack()
        }
    }

    private fun editAddress(oldAddress: Address) {
        val addressTitle = binding.etAddressTitle.text.toString()
        val addressDetailedLocation = binding.etDetailedLocation.text.toString()
        val img = R.drawable.office

        if (addressTitle.isEmpty() || addressDetailedLocation.isEmpty()) {
            Toast.makeText(requireContext(), "Fields should not be empty", Toast.LENGTH_SHORT)
                .show()
        } else {
            val address = Address(
                id = oldAddress.id,
                img = img,
                title = addressTitle,
                detailedLocation = addressDetailedLocation
            )

            val result = bundleOf(CHANGED_ADDRESS_KEY to address)
            parentFragmentManager.setFragmentResult(CHANGED_ADDRESS_REQUEST_KEY, result)

            parentFragmentManager.popBackStack()
        }
    }

}