package com.example.tbcexercises.presentation.userListScreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.App
import com.example.tbcexercises.R
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentUserListBinding
import com.example.tbcexercises.presentation.userListScreen.adapter.UserListAdapter
import com.example.tbcexercises.util.Resource
import com.example.tbcexercises.util.collectLastState
import com.example.tbcexercises.util.toast


class UserListFragment : BaseFragment<FragmentUserListBinding>(FragmentUserListBinding::inflate) {
    private val viewModel: UserListViewModel by viewModels {
        UserListVIewModelFactory((requireActivity().application as App).repository)
    }
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter()
    }

    override fun start() {
        viewModel.getUserRemote(checkForInternet(requireContext()))

        binding.rvUserList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvUserList.adapter = userListAdapter

        collectLastState(viewModel.users) {
            userListAdapter.submitList(it)
        }

        if (!checkForInternet(requireContext())) {
            showLoadingScreen(false)
            toast(getString(R.string.you_are_offline))
        } else {
            collectLastState(viewModel.userRemote) { state ->
                when (state) {
                    is Resource.Error -> {
                        toast(getString(R.string.error))
                    }

                    Resource.Loading -> {
                        showLoadingScreen(true)
                    }

                    is Resource.Success -> {
                        showLoadingScreen(false)
                    }
                }
            }

            toast(getString(R.string.you_are_online))

        }
    }

    override fun listeners() {
    }

    //Code copied from https://www.geeksforgeeks.org/how-to-check-internet-connection-in-kotlin/

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun showLoadingScreen(loading: Boolean) {
        binding.rvUserList.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

}