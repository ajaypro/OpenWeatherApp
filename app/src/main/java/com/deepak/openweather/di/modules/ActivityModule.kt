package com.deepak.openweather.di.modules

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.deepak.openweather.data.LocationRepository
import com.deepak.openweather.data.Main
import com.deepak.openweather.data.WeatherProperty
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.WeatherApi
import com.deepak.openweather.ui.Factory
import com.deepak.openweather.ui.MainActivity
import com.deepak.openweather.ui.WeatherViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides
    fun providesWeatherViewModel(weatherRepository: WeatherRepository, application: Application): WeatherViewModel =
        ViewModelProvider(activity, Factory(weatherRepository, application))
            .get(WeatherViewModel::class.java)


}