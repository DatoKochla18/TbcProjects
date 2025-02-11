package com.example.tbcexercises.presentation.userListScreen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.App
import com.example.tbcexercises.databinding.FragmentUserListBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.userListScreen.userListAdapater.UserListAdapter
import com.example.tbcexercises.presentation.userListScreen.userLoadState.UserLoadStateAdapter
import com.example.tbcexercises.presentation.userListScreen.viewmodel.UserListViewModel
import com.example.tbcexercises.presentation.userListScreen.viewmodel.UserListViewModelFactory
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast


class UserListFragment : BaseFragment<FragmentUserListBinding>(FragmentUserListBinding::inflate) {
    private val viewModel: UserListViewModel by viewModels {
        UserListViewModelFactory((requireActivity().application as App).userRepository)
    }
    private val userListAdapter by lazy {
        UserListAdapter()
    }

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

            val isInitialLoading = refreshState is LoadState.Loading && userListAdapter.itemCount == 0

            binding.apply {
                progressBar.isVisible = isInitialLoading
                rvContainer.isVisible = refreshState is LoadState.NotLoading || userListAdapter.itemCount > 0
                btnRetry.isVisible = refreshState is LoadState.Error && userListAdapter.itemCount == 0
            }

            handleError(loadState)
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