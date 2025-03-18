package com.example.tbcexercises.presentation.screen.search

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentSearchBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLastFlow
import com.example.tbcexercises.presentation.extension.toast
import com.example.tbcexercises.presentation.screen.search.category_adapter.CategoryAdapter
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

    override fun listeners() {
        binding.etSearch.doAfterTextChanged { editable ->

            viewModel.onEvent(SearchUiEvent.SearchCategories(editable.toString()))
        }
    }

    private fun updateUi(state: SearchUiState) {
        categoryAdapter.submitList(state.categories.toList())

        binding.apply {
            progressBar.isVisible = state.isLoading

        }
    }

    private fun setUpRecycler() {
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}
