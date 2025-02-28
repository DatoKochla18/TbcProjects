package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.domain.repository.LocationRepository
import com.example.tbcexercises.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {

    private val _locationFlow = MutableStateFlow<Resource<List<Location>>?>(null)
    val locationFlow: StateFlow<Resource<List<Location>>?> = _locationFlow

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getLocations().collectLatest {
                _locationFlow.value = it
            }
        }
    }

}