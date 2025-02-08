package com.example.tbcexercises.presentation.productListScreen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.App
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.common.collectLastState
import com.example.tbcexercises.common.toast
import com.example.tbcexercises.data.imageLoader.CoilImageLoader
import com.example.tbcexercises.data.imageLoader.GlideImageLoader
import com.example.tbcexercises.databinding.FragmentProductListBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.productListScreen.adapter.ProductListAdapter


class ProductListFragment :
    BaseFragment<FragmentProductListBinding>(FragmentProductListBinding::inflate) {
    private val viewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory((requireActivity().application as App).repository)
    }
    private val productListAdapter: ProductListAdapter by lazy {
        ProductListAdapter(onClick = {}, imageLoader = CoilImageLoader)
    }

    override fun start() {
        binding.rvProductList.apply {
            adapter = productListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        collectLastState(viewModel.products) { state ->
            when (state) {
                is Resource.Success -> {
                    showLoadingScreen(false)
                    productListAdapter.submitList(state.data)
                }

                is Resource.Error -> toast(state.message)
                Resource.Loading -> {
                    showLoadingScreen(true)
                }
            }
        }
    }

    override fun listeners() {
    }

    private fun showLoadingScreen(loading: Boolean) {
        binding.apply {
            rvProductList.isVisible = !loading
            progressBar.isVisible = loading
        }
    }


}