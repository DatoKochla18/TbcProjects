package com.example.tbcexercises.presentation.search

import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tbcexercises.databinding.FragmentSearchBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.search.category_adapter.CategoryAdapter
import com.example.tbcexercises.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    // Get your ViewModel (assuming Hilt or another DI mechanism is used)
    private val viewModel: SearchViewModel by viewModels()

    // Adapter for your RecyclerView (implement this based on your requirements)
    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun start() {
        // Setup RecyclerView
        binding.rvCategories.apply {
            adapter = categoryAdapter
            // Setup layout manager if needed, e.g. LinearLayoutManager(context)
        }

        // Observe the categories state from the ViewModel
        lifecycleScope.launchWhenStarted {
            viewModel.categoriesState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show a loading indicator if needed
                    }

                    is Resource.Success -> {
                        // Update the adapter with the new list of categories
                        categoryAdapter.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        // Display error message
                        showToast(resource.message)
                        Log.d("error", resource.message)
                    }
                }
            }
        }

        // Listen to text changes in the search EditText
        binding.etSearch.doAfterTextChanged { editable ->
            viewModel.onSearchTextChanged(editable?.toString().orEmpty())
        }
    }

    override fun listeners() {
        // Any additional listeners can be set up here.
    }

    // Optional helper to show Toast messages
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}