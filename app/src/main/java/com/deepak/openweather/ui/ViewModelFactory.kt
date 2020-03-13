package com.deepak.openweather.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deepak.openweather.data.WeatherRepository

@Suppress("UNCHECKED_CAST")
class Factory(private val weatherRepository: WeatherRepository, private val application: Application): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
           return WeatherViewModel(weatherRepository, application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}