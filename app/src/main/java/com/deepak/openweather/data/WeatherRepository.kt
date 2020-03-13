package com.deepak.openweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherDatabase: WeatherDatabase,
                        private val weatherService: WeatherApi){

    private val TAG = "WeatherRepository"

    /**
     * Does a network call and saves data in db
     */
    suspend fun refreshData(array: DoubleArray?) {

        Log.i(TAG, Arrays.toString(array))

        if (array!!.isNotEmpty()) {

            withContext(Dispatchers.IO) {
                weatherService.getWeatherAsync(array[0], array[1]).await().apply {
                    weatherDatabase.weatherDao.insertAll(this)
                }
            }
        }
    }

    /**
     * Livedata to provide the latest changes from db
     */
    val weatherList: LiveData<WeatherProperty> = weatherDatabase.weatherDao.get()


}