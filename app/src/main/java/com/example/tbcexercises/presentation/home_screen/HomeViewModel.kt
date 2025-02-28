package com.example.tbcexercises.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.domain.repository.LocationRepository
import com.example.tbcexercises.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    fun getLocationByLatLong(lat: Double, long: Double): Flow<Resource<Location?>?> = flow {
        _locationFlow.value?.let { resource ->
            when (resource) {
                is Resource.Success -> {
                    val location = resource.data.find { it.lat == lat && it.lan == long }
                    emit(Resource.Success(location))
                }

                is Resource.Error -> emit(Resource.Error(resource.message))
                else -> emit(null)
            }
        }
    }.flowOn(Dispatchers.IO)

}