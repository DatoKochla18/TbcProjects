package com.example.tbcexercises.presentation.productDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.domain.model.Product
import com.example.tbcexercises.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _product = MutableStateFlow<Resource<Product>>(Resource.Loading)
    val product: StateFlow<Resource<Product>> = _product


    fun fetchProduct(id: Int) {
        viewModelScope.launch {
            productRepository.getProduct(id).collectLatest { resource ->
                _product.value = resource
            }
        }
    }
}