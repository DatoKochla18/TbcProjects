package com.example.tbcexercises.presentation.userListScreen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentUserListBinding
import com.example.tbcexercises.presentation.userListScreen.adapter.UserListAdapter
import com.example.tbcexercises.presentation.userListScreen.userLoadState.UserLoadStateAdapter
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast


class UserListFragment : BaseFragment<FragmentUserListBinding>(FragmentUserListBinding::inflate) {
    private val viewModel: UserListViewModel by viewModels()
    private val userListAdapter by lazy {
        UserListAdapter()
    }

    override fun start() {
        setUpRecycleView()

        collectLastState(viewModel.users) {
            userListAdapter.submitData(it)
        }

    }

    override fun listeners() {
        binding.btnRetry.setOnClickListener {
            userListAdapter.retry()
        }


        userListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                rvContainer.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
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