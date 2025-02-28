package com.example.baseproject.ui.locations

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLocationsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(FragmentLocationsBinding::inflate),
    OnMapReadyCallback {

    private val locationsViewModel: LocationsViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mapFragment: SupportMapFragment? = null
    private lateinit var clusterManager: ClusterManager<MyItem>

    override fun start() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkInitialState()
    }

    override fun listeners() {

    }

    private val locationSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        handleLocationSettingsResult()
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        handlePermissionResult(permissions)
    }

    private fun checkInitialState() {
        when {
            hasLocationPermissions() && isLocationEnabled() -> setUpMap()
            hasLocationPermissions() -> promptUserToEnableLocation()
            else -> requestLocationPermissions()
        }
    }

    private fun setUpMap() {
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map_fragment, it)
                    .commit()
            }
        }
        mapFragment?.getMapAsync(this)
    }

    private fun hasLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun handlePermissionResult(permissions: Map<String, Boolean>) {
        when {
            permissions.any { it.value } -> {
                if (isLocationEnabled()) {
                    setUpMap()
                } else {
                    promptUserToEnableLocation()
                }
            }

            else -> Toast.makeText(
                requireContext(),
                getString(R.string.location_permission_required),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleLocationSettingsResult() {
        if (isLocationEnabled()) {
            setUpMap()
        } else {
            Toast.makeText(requireContext(),
                getString(R.string.location_services_still_disabled), Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun requestLocationPermissions() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun moveToCurrentLocation() {
        if (!hasLocationPermissions()) {
            requestLocationPermissions() // Request permissions if not granted
            return
        }

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_get_location, exception.message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(),
                getString(R.string.location_permission_is_required), Toast.LENGTH_LONG)
                .show()
            requestLocationPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)
    }

    private fun promptUserToEnableLocation() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.location_services_disabled))
            .setMessage(getString(R.string.would_you_like_to_go_to_settings_to_enable_location_services))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                locationSettingsLauncher.launch(intent)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map.apply {
            uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isZoomControlsEnabled = true
        }
        setUpClusterer()

        if (hasLocationPermissions() && isLocationEnabled()) {
            moveToCurrentLocation()
            observeMarkers()
        }
    }

    private fun setUpClusterer() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.503186, -0.126446), 10f))
        clusterManager = ClusterManager(context, googleMap)

        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        clusterManager.setOnClusterItemClickListener { item ->
            val action =
                LocationsFragmentDirections.actionLocationsFragmentToMarkerInfoBottomSheetDialogFragment(
                    title = item.title,
                    body = item.snippet
                )
            findNavController().navigate(action)
            true
        }
    }

    private fun observeMarkers() {
        viewLifecycleOwner.lifecycleScope.launch {
            locationsViewModel.locationsStateFlow.collectLatest { state ->
                when (state) {
                    is LocationsUiState.Loading -> {}
                    is LocationsUiState.Success -> {
                        val locations = state.locations
                        locations.forEach { location ->
                            clusterManager.addItem(
                                MyItem(
                                    lat = location.lat,
                                    lng = location.lan,
                                    snippet = location.address,
                                    title = location.title
                                )
                            )
                        }
                    }

                    is LocationsUiState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_fetching_locations),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is LocationsUiState.Idle -> {}
                }
            }
        }
    }
}