package com.example.gpslesson

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gpslesson.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        // Use your map there
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val markerInSydney = MarkerOptions().position(sydney).title("Marker in Sydney")

        mMap.addMarker(markerInSydney)
        val address = getAddressFromLocation(sydney)
        val location = getLocationFromAddress("Харьков")
        // moving and scaling camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-34.0, 151.0), 10.0f))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-34.0, 151.0), 10.0f))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))

    }

    fun scaleMapByPoints() {
        val latLngBounds = LatLngBounds.Builder()
            .include(LatLng(-33.0, 155.0))
            .include(LatLng(-36.0, 150.0))
            .build()
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 300))
    }

    fun drawPolyline() {
        mMap.apply {
            addPolyline(
                PolylineOptions()
                    .add(LatLng(-33.0, 155.0))
                    .add(LatLng(-36.0, 151.0))
                    .add(LatLng(-32.0, 150.0))
                    .color(ContextCompat.getColor(this@MapsActivity, R.color.purple_200))
                    .jointType(JointType.ROUND)
                    .startCap(RoundCap())
                    .endCap(RoundCap())
            )
        }

    }

    fun getAddressFromLocation(latLng: LatLng): Address? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (addresses.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName
            return addresses[0]
        }
        return null
    }

    fun getLocationFromAddress(strAddress: String): LatLng? {
        val coder = Geocoder(this)
        try {
            val address = coder.getFromLocationName(strAddress, 5)
            if (address.isNotEmpty()) {
                // use a location
                return LatLng(address[0].latitude, address[0].longitude)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return null
    }

}
