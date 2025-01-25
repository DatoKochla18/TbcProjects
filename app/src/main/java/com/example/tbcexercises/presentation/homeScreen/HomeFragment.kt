package com.example.tbcexercises.presentation.homeScreen


import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.homeScreen.adapter.UserAdapter
import com.example.tbcexercises.utils.exntension.collectLastState
import com.example.tbcexercises.utils.exntension.toast


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    private val homeAdapter by lazy {
        UserAdapter()
    }

    override fun start() {
        binding.rvContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = homeAdapter
        }
        collectLastState(viewModel.homeState) { homeState ->
            showLoadingScreen(homeState.loading)
            homeState.success?.let {
                homeAdapter.submitList(it.data.toList())
            }
            homeState.error?.let { toast(getString(it)) }
        }
    }

    override fun listeners() {
        binding.imgMyProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        val viewVisibility = if (!isLoading) View.VISIBLE else View.GONE

        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

            imgMyProfile.visibility = viewVisibility
            txtMyProfile.visibility = viewVisibility
            rvContainer.visibility = viewVisibility
        }
    }

}