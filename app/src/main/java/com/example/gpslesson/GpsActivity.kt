package com.example.gpslesson

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class GpsActivity : AppCompatActivity() {

    private val permissionRequestCode = 34
    private val permissionList = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    lateinit var tvGps:TextView

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // 1)
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                setupLocationListener()
//                createLocationRequest()
//            } else {
//                Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT).show()
//
//            }
//        }

    val locationCallback = object :LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                // Update UI with location data
                val text = "Updated location: ${location.latitude}, ${location.longitude}"
                tvGps.text = text
            }
            super.onLocationResult(locationResult)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        tvGps = findViewById(R.id.tvGps)
        checkLocationPermission()

    }


    private fun checkLocationPermission() {
        var isGranted = true

        permissionList.forEach {
            isGranted = isGranted && ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
        if(isGranted) {
            setupLocationListener()
            createLocationRequest()
        }
        else {
            // 1)
//            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            // 2)
            ActivityCompat.requestPermissions(this, permissionList, permissionRequestCode)
        }

    }

    // 2)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            permissionRequestCode -> {
                // If request is cancelled, the result arrays are empty.
                var isGranted = grantResults.isNotEmpty()
                grantResults.forEach {
                    isGranted = it == PackageManager.PERMISSION_GRANTED
                }

                if (isGranted) {
                    // continue work and use gps
                    setupLocationListener()
                    createLocationRequest()
                } else {
                    // cannot use gps
                    Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> Unit
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    fun setupLocationListener() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if(location!=null) {
                    val text = "location: ${location.latitude}, ${location.longitude}"
                    tvGps.text = text
                }
                else {
                    tvGps.text = "No location"
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun createLocationRequest() {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task
            .addOnSuccessListener { locationSettingsResponse ->

                fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper())

            }
            .addOnFailureListener {exception ->
                tvGps.text = "No location updates"
            }


    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}