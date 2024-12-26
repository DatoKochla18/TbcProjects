package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {
    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    private val data = getData()
    private val addressAdapter: AddressAdapter by lazy {
        AddressAdapter(removeItem = { address ->
            data.remove(address)
            addressAdapter.submitList(data.toList())
        }, editItem = { address ->
            editAddress(address)
        }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddressBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.rvAddressCard.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }

        addressAdapter.submitList(data.toList())
        listeners()

        parentFragmentManager.setFragmentResultListener(
            NEW_ADDRESS_REQUEST_KEY,
            this
        ) { _, bundle ->
            val address = bundle.getParcelable<Address>(NEW_ADDRESS_KEY)!!

            data.add(0, address)
            addressAdapter.submitList(data.toList())
        }
    }

    private fun listeners() {
        binding.btnAddNewUser.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fvContainer, AddEditFragment())
                addToBackStack(null)
                commit()
            }
        }

        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun editAddress(address: Address) {
        parentFragmentManager.beginTransaction().apply {
            val bundle = bundleOf(OLD_ADDRESS_KEY to address)
            val addressFragment = AddEditFragment().apply { arguments = bundle }
            replace(R.id.fvContainer, addressFragment)
            addToBackStack(null)
            commit()
        }
        Log.d("edit","executed")
        parentFragmentManager.setFragmentResultListener(
            CHANGED_ADDRESS_REQUEST_KEY, this
        ) { _, bundle ->
            val changedAddress = bundle.getParcelable<Address>(CHANGED_ADDRESS_KEY)!!
            val idx = data.indexOfFirst { it.id == changedAddress.id }

            data[idx] = changedAddress
            addressAdapter.submitList(data.toList())
        }
    }
}
