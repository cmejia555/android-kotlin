package com.cmejia.kotlinapp.fragments

import android.Manifest
import android.content.Context

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.cmejia.kotlinapp.R
import com.google.android.gms.location.*


class MapFragment : Fragment() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        setUpLocationRequest()
        enableMyLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latitudeTextView = view.findViewById(R.id.latitude_text)
        longitudeTextView = view.findViewById(R.id.longitude_text)
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            //setUpLocationRequest()
            getLastLocation()
        } else {
            requestPermissions()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    private fun setUpLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it == null) {
                if (!isLocationEnabled()) {
                    startLocationUpdates()
                    Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "ERRRORRRR", Toast.LENGTH_LONG).show()
                    requestNewLocationClient()
                }
            } else {
                setLocation(it)
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager : LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun startLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            setLocation(location)
        }
    }

    private fun requestNewLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        startLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults.contains(PackageManager.PERMISSION_GRANTED))) {
                enableMyLocation()
            }
        }
    }

    private fun setLocation(location: Location) {
        latitudeTextView.text = getString(R.string.latitude_text, location.latitude.toString())
        longitudeTextView.text = getString(R.string.longitude_text, location.longitude.toString())
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 42
    }

    override fun onResume() {
        super.onResume()
        if (isLocationEnabled()) {
            startLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}
