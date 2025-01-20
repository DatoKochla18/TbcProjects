package com.example.tbcexercises.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.BaseFragment
import com.example.tbcexercises.databinding.FragmentHomeScreenBinding


class HomeScreenFragment :
    BaseFragment<FragmentHomeScreenBinding>(FragmentHomeScreenBinding::inflate) {
    private val viewModel: HomeScreenViewModel by viewModels()
    private val messageAdapter by lazy {
        MessageAdapter()
    }

    override fun start() {
        Log.d("tag", viewModel.decodedData.toString())
        binding.rvContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = messageAdapter
        }

        messageAdapter.submitList(viewModel.decodedData?.toList())
    }

    override fun listeners() {
        binding.btnFilter.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            messageAdapter.submitList(viewModel.decodedData?.filter { it.owner.contains(searchText) })
        }
    }

}