package com.example.tbcexercises.presentation.search

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentSearchBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLastFlow
import com.example.tbcexercises.presentation.extension.toast
import com.example.tbcexercises.presentation.search.category_adapter.CategoryAdapter
import com.example.tbcexercises.presentation.util.Constants.TIME_BEFORE_FIRING_REQUEST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun start() {
        setUpRecycler()
        collectLastFlow(viewModel.state) { state ->
            updateUi(state)
        }

        collectLastFlow(viewModel.uiEvent) { event ->
            when (event) {
                is SearchSideEffects.ShowError -> toast(event.error)
            }
        }
    }

    private fun updateUi(state: SearchUiState) {
        Log.d("state", state.toString())
        binding.apply {
            progressBar.isVisible = state.isLoading
            rvCategories.isVisible = !state.isLoading
        }
        categoryAdapter.submitList(state.categories.toList())
        Log.d("statedata", state.categories.toList().toString())
    }

    private fun setUpRecycler() {
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val searchRunnable = Runnable {
        val query = binding.etSearch.text?.toString().orEmpty()
        viewModel.onEvent(SearchUiEvent.SearchCategories(query))
    }

    override fun listeners() {
        binding.etSearch.doAfterTextChanged { editable ->
            Log.d("stateChangedText", editable.toString())
            binding.etSearch.removeCallbacks(searchRunnable)
            binding.etSearch.postDelayed(searchRunnable, TIME_BEFORE_FIRING_REQUEST)
        }
    }
}
