package com.example.tbcexercises.feature_user.presentation.home_screen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.core.presentation.base.BaseFragment
import com.example.tbcexercises.core.presentation.extension.collectLastState
import com.example.tbcexercises.core.presentation.extension.toast
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.feature_user.presentation.home_screen.adapter.UserListAdapter
import com.example.tbcexercises.feature_user.presentation.home_screen.userLoadState.UserLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    private val userListAdapter by lazy {
        UserListAdapter()
    }

    override fun start() {
        setUpRecycleView()


        collectLastState(viewModel.uiState) { uiState ->
            uiState.users?.let { pagingData ->
                userListAdapter.submitData(pagingData)
            }
            binding.progressBar.isVisible = uiState.isLoading
            binding.btnRetry.isVisible =
                uiState.errorMessage != null && userListAdapter.itemCount == 0

            binding.rvContainer.isVisible = !uiState.isLoading && userListAdapter.itemCount > 0
        }

        collectLastState(viewModel.events) { event ->
            when (event) {
                is HomeEvent.ShowError -> toast(event.message)
            }
        }
        listeners()

    }

    private fun listeners() {
        binding.btnRetry.setOnClickListener {
            userListAdapter.retry()
        }

        binding.imgMyProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }

    }

    private fun setUpRecycleView() {
        binding.rvContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userListAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter { userListAdapter.retry() }
            )
        }

        userListAdapter.addLoadStateListener { loadState ->
            viewModel.onLoadStateChanged(loadState)
        }
    }
}