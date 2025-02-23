package com.example.tbcexercises.presentation.home_screen

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.home_screen.parent_adapter.ParentListAdapter
import com.example.tbcexercises.util.extension.collectLastState
import com.example.tbcexercises.util.image_loader.ImageLoader
import com.example.tbcexercises.util.network_helper.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: HomeViewModel by viewModels()

    private val parentAdapter by lazy {
        ParentListAdapter(imageLoader)
    }

    override fun start() {
        binding.rvParent.apply {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        collectLastState(viewModel.parentItems) { state ->
            when (state) {
                is Resource.Error -> {
                }

                Resource.Loading -> {
                }

                is Resource.Success -> {
                    parentAdapter.submitList(state.data)
                }

                null -> {}
            }
        }
    }
}
