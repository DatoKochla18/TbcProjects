package com.example.tbcexercises.presentation.screen.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.hideKeyboard
import com.example.tbcexercises.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewmodel: HomeViewModel by viewModels()

    private val homeAdapter by lazy {
        ImageAdapter(onClick = { viewmodel.process(HomeViewModel.HomeEvents.ClickedOnImage(it))
        Log.d("hex",it)})
    }

    override fun start() {
        binding.laHome.rvImage.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        collectLatestFlow(viewmodel.state) { updateUi(it) }

        collectLatestFlow(viewmodel.effects) { getEffects(it) }

    }

    private fun updateUi(state: HomeViewModel.HomeUiState) {
        homeAdapter.submitList(state.images)

        Log.d("home state", state.toString())


        binding.laLoading.root.isVisible = state.isLoading
        binding.laNoInternet.root.isVisible = !state.isConnected

        if (state.isConnected) {
            binding.root.hideKeyboard()
        }

    }

    private fun getEffects(homeSideEffect: HomeViewModel.HomeSideEffect) {
        when (homeSideEffect) {
            is HomeViewModel.HomeSideEffect.NavigateToDetailScreen -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        homeSideEffect.hex
                    )
                )
            }

            is HomeViewModel.HomeSideEffect.ShowError -> {
                binding.root.showSnackBar(getString(homeSideEffect.message))
            }
        }
    }

    override fun listeners() {
        binding.laNoInternet.btnTryAgain.setOnClickListener {
            viewmodel.process(HomeViewModel.HomeEvents.GetImages)
        }

        binding.laHome.etSearchView.doAfterTextChanged {
            viewmodel.process(HomeViewModel.HomeEvents.SearchImages(it.toString()))
        }
    }


}