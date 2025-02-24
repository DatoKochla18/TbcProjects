package com.example.tbcexercises.presentation.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.DetailProduct
import com.example.tbcexercises.domain.repository.product.HomeProductRepository
import com.example.tbcexercises.utils.network_helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: HomeProductRepository) :
    ViewModel() {


    private val _DetailProductState =
        MutableStateFlow<Resource<List<DetailProduct>>>(Resource.Loading)

    val detailProductState: StateFlow<Resource<List<DetailProduct>>> =
        _DetailProductState

    fun getProduct(id: Int) {
        viewModelScope.launch {
            productRepository.getProductById(id).collectLatest { state ->
                _DetailProductState.value = state
            }
        }
    }
}