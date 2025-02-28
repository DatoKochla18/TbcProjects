package com.example.tbcexercises.presentation.home_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.domain.model.Location
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.collectLastState
import com.example.tbcexercises.utils.toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){

    private val viewModel: HomeViewModel by viewModels()
    private var googleMap: GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            toast("Location permission granted")
            setupMap()
            getCurrentLocation()
        } else {
            toast("Location permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()
    }

    override fun start() {
        setupMap()
    }

    override fun onResume() {
        super.onResume()
        checkLocationEnabled()
    }

    override fun listeners() {

        binding.apply {
            imbIncrease.setOnClickListener {
                zoom(1f)
            }
            imbdecrease.setOnClickListener {
                zoom(-1f)
            }
        }


    }

    private fun zoom(float: Float) {
        googleMap?.let { map ->
            val currentZoom = map.cameraPosition.zoom
            val newZoom = currentZoom - float
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    map.cameraPosition.target,
                    newZoom
                )
            )
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            toast("Location permission already granted")
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fvcMap) as? SupportMapFragment
        mapFragment?.getMapAsync { map ->
            Log.d("MapFragment", "Google Map is ready")
            googleMap = map

            map.setOnMarkerClickListener { marker ->
                getMarker(marker)
                true
            }

            getCurrentLocation()

            updateMapWithLocations(map)
        }

    }

    private fun getMarker(marker: Marker) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLocationByLatLong(
                    marker.position.latitude,
                    marker.position.longitude
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            val location = result.data
                            location?.let {
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToBottomSheetLocationFragment(
                                        lat = location.lat.toFloat(),
                                        lan = location.lan.toFloat(),
                                        title = location.title,
                                        location.address
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            toast(result.message)
                        }

                        else -> Unit
                    }
                }
            }
        }

    }

    private fun checkLocationEnabled() {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            showLocationSettingsDialog()
        } else {
            getCurrentLocation()
            googleMap?.let { updateMapWithLocations(it) }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true

            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }
        }
    }

    private fun showLocationSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.enable_location))
            .setMessage(getString(R.string.your_location_settings_are_turned_off_please_enable_location_services_to_continue))
            .setPositiveButton(getString(R.string.settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }


    private fun updateMapWithLocations(map: GoogleMap) {
        collectLastState(viewModel.locationFlow) { state ->
            when (state) {
                is Resource.Error -> {
                    toast("Error loading locations: ${state.message}")
                    showLoading(false)

                }

                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val locationList = state.data
                    for (location in locationList) {
                        addMarker(map, location)
                    }
                }

                null -> {
                }
            }
        }
    }

    private fun addMarker(map: GoogleMap, locationData: Location) {
        val position = LatLng(locationData.lat, locationData.lan)
        map.addMarker(MarkerOptions().position(position).title(locationData.title))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            fvcMap.isVisible = !isLoading
            progressbar.isVisible = isLoading
            imbIncrease.isVisible = !isLoading
            imbdecrease.isVisible = !isLoading
            txtFetchingData.isVisible = isLoading
        }
    }
}
