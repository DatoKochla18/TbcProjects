package com.example.tbcexercises.presentation.productListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.domain.model.Product
import com.example.tbcexercises.domain.repository.ProductRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading)
    val products: StateFlow<Resource<List<Product>>> = _products

    init {
        fetchProducts(20)
    }

    private fun fetchProducts(limit: Int) {
        viewModelScope.launch {
            productRepository.getProducts(limit).collectLatest { resource ->
                _products.value = resource
            }
        }
    }
}