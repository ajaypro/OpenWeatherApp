package com.deepak.openweather.data

import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

/**
 * To get current location of device using fusedlocation provider
 *
 */

class LocationRepository(context: Context) {

    private val TAG = "LocationRepository"


    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var _currentLocation = MutableLiveData<Location>()
    val currentLocaton: LiveData<Location> = _currentLocation

    init {

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            it?.let {
                _currentLocation.value = it
                Log.i("LocationRepository", it.toString())
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
            Log.i(TAG, "getLastLocation: $it")
        }
    }


    /**
     * Building location requests to get location accuracy within 5 secs of movement
     * and also use in user location settings
     */
    companion object {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult ?: return
        }
    }

    /**
     * Stoping the location updates
     */

    fun stopLocationUpdates() {
        Log.i(TAG, "removed location updates")
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


}