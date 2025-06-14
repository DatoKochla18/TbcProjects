package com.example.tbcexercises.presentation.screen.home_screen

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.showSnackBar
import com.example.tbcexercises.presentation.screen.home_screen.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewmodel by viewModels<HomeViewModel>()

    private val homeAdapter by lazy {
        HomeAdapter(onClick = { viewmodel.process(HomeEvent.ClickedOnItem(it)) })
    }

    override fun start() {
        binding.laHome.rvBreeds.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        collectLatestFlow(viewmodel.state) {
            updateUi(it)
        }

        collectLatestFlow(viewmodel.effects) {
            when (it) {
                is HomeSideEffect.NavigateToDetail -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.name)
                    )
                }

                is HomeSideEffect.ShowError -> {
                    binding.root.showSnackBar(getString(it.message))
                }
            }
        }

        viewmodel.process(HomeEvent.GetBreeds)
    }

    override fun listeners() {
        binding.laHome.etSearchView.doAfterTextChanged {
            viewmodel.process(HomeEvent.SearchBreads(it.toString()))
        }

        binding.laNoInternet.btnTryAgain.setOnClickListener {
            viewmodel.process(HomeEvent.GetBreeds)
        }
    }

    private fun updateUi(homeUiState: HomeUiState) {
        homeAdapter.submitList(homeUiState.breeds)
        when {
            !homeUiState.isInternet -> {
                binding.laHome.root.isVisible = false
                binding.laLoading.root.isVisible = false
                binding.laNoInternet.root.isVisible = true
            }

            homeUiState.isLoading -> {
                binding.laLoading.root.isVisible = true
                binding.laNoInternet.root.isVisible = false
            }

            else -> {
                binding.laHome.root.isVisible = true
                binding.laLoading.root.isVisible = false
                binding.laNoInternet.root.isVisible = false
            }
        }
    }
}