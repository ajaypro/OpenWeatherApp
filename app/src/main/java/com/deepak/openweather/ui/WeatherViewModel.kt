package com.deepak.openweather.ui

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.deepak.openweather.data.LocationRepository
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.work.RefreshDataWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WeatherViewModel(private val weatherRepository: WeatherRepository, application: Application) :
    AndroidViewModel(application) {

    private val TAG = "WeatherViewModel"

    private val locationRepository = LocationRepository(application)

    init {

        locationRepository.currentLocaton.observeForever{
            it?.let {
                refreshDataFromRepository(it)
            }
        }

    }

    private fun refreshDataFromRepository(location: Location) {

        viewModelScope.launch {

            setUpRecurringWork(location)
            Log.i(TAG, "Executing workmanager")
        }
    }

    private fun setUpRecurringWork(location: Location){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val data = Data.Builder()
            .putDoubleArray("location", doubleArrayOf(location.latitude, location.longitude))
            .build()

        val recurringRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        Log.d(TAG,"Periodic Work request for sync is scheduled")
        WorkManager.getInstance(getApplication())
            .enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                recurringRequest)
    }


    /**
     * currentDayWeather livedata which will be observed by databinding in of activity_main.xml
     */
    val currentDayWeather = weatherRepository.weatherList.also {
        Log.i("WeatherViewModel", "currentDayWeather:  ${it.value.toString()}")
    }


    /**
     * Clearing stoplocation when the viewmodel also is cleared
     */
    override fun onCleared() {
        super.onCleared()
        Log.i("WeatherViewModel", "onCleared")
        locationRepository.stopLocationUpdates()
    }
}