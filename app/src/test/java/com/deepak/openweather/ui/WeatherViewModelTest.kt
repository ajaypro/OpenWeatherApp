package com.deepak.openweather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deepak.openweather.data.WeatherRepository
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest{

    lateinit var viewModel: WeatherViewModel

    lateinit var weatherRepository: WeatherRepository

    // For livedata
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    fun setUp(){
        viewModel = WeatherViewModel(, ApplicationProvider.getApplicationContext<>())
    }




}