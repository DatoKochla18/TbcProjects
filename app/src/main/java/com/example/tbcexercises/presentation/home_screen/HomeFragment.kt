package com.example.tbcexercises.presentation.home_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
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
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnMapReadyCallback {

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
        mapFragment?.getMapAsync(this)
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
            .setTitle("Enable Location")
            .setMessage("Your location settings are turned off. Please enable location services to continue.")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map

        getCurrentLocation()

        updateMapWithLocations(map)
    }

    private fun updateMapWithLocations(map: GoogleMap) {
        collectLastState(viewModel.locationFlow) { state ->
            when (state) {
                is Resource.Error -> {
                    toast("Error loading locations: ${state.message}")
                }

                is Resource.Loading -> {
                }

                is Resource.Success -> {
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
}