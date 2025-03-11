package com.example.tbcexercises.presentation.home_screen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLastState
import com.example.tbcexercises.presentation.extension.toast
import com.example.tbcexercises.presentation.home_screen.adapter.UserListAdapter
import com.example.tbcexercises.presentation.home_screen.userLoadState.UserLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    private val userListAdapter by lazy {
        UserListAdapter()
    }

    override fun start() {
        setUpRecycleView()

        collectLastState(viewModel.usersFlow) {
            userListAdapter.submitData(it)
        }

        collectLastState(userListAdapter.loadStateFlow) { loadState ->
            updateUIVisibility(loadState)
            handleError(loadState)

        }


    }

    private fun updateUIVisibility(loadState: CombinedLoadStates) {
        val refreshState = loadState.mediator?.refresh ?: loadState.source.refresh

        val isInitialLoading =
            refreshState is LoadState.Loading && userListAdapter.itemCount == 0

        binding.apply {
            progressBar.isVisible = isInitialLoading
            rvContainer.isVisible =
                refreshState is LoadState.NotLoading || userListAdapter.itemCount > 0
            btnRetry.isVisible =
                refreshState is LoadState.Error && userListAdapter.itemCount == 0
        }
    }

    override fun listeners() {
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
                footer = UserLoadStateAdapter(userListAdapter::retry)
            )
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            toast(it.error.toString())
        }
    }

}