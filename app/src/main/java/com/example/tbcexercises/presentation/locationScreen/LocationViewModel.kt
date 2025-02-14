package com.example.tbcexercises.presentation.locationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.domain.repository.LocationRepository
import com.example.tbcexercises.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    private val _locationResponse = MutableStateFlow<Resource<List<Location>>?>(null)
    val locationResponse: StateFlow<Resource<List<Location>>?> = _locationResponse

    init {
        getLocation()
    }

    private fun getLocation() {
        _locationResponse.value = Resource.Loading

        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getUsers().collectLatest { state ->
                _locationResponse.value = state
            }
        }
    }

}