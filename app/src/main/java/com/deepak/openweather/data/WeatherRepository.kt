package com.deepak.openweather.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.Networking
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class WeatherRepository(private val weatherDatabase: WeatherDatabase, context: Context) {

    private val TAG = "WeatherRepository"


    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    suspend fun refreshData(array: DoubleArray?) {

        Log.i(TAG, Arrays.toString(array))

        if (array!!.isNotEmpty()) {

            withContext(Dispatchers.IO) {
                Networking.retrofitService.getWeatherAsync(array[0], array[1]).await().apply {
                    weatherDatabase.weatherDao.insertAll(this)
                }
            }
        }
    }

    val weatherList: LiveData<WeatherProperty> = weatherDatabase.weatherDao.get()


}