package com.example.tbcexercises.presentation.homeScreen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.homeScreen.adapter.UserListAdapter
import com.example.tbcexercises.presentation.homeScreen.userLoadState.UserLoadStateAdapter
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var userListAdapter: UserListAdapter

    override fun start() {
        setUpRecycleView()

        collectLastState(viewModel.usersFlow) {
            userListAdapter.submitData(it)
        }

    }

    override fun listeners() {
        binding.btnRetry.setOnClickListener {
            userListAdapter.retry()
        }


        userListAdapter.addLoadStateListener { loadState ->
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

            handleError(loadState)
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