package com.example.tbcexercises.presentation

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.data.util.BaseFragment
import com.example.tbcexercises.databinding.FragmentHomeScreenBinding
import com.example.tbcexercises.presentation.homeScreen.adapter.MessageAdapter


class HomeScreenFragment :
    BaseFragment<FragmentHomeScreenBinding>(FragmentHomeScreenBinding::inflate) {

    private val viewModel: HomeScreenViewModel by viewModels()
    private val messageAdapter by lazy {
        MessageAdapter()
    }

    override fun start() {
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