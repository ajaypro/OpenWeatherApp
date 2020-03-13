package com.deepak.openweather.di.component

import android.app.Application
import android.content.Context
import com.deepak.openweather.WeatherApplication
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.WeatherApi
import com.deepak.openweather.di.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {

        fun inject(application: WeatherApplication)

       fun getApplication(): Application

       fun getContext(): Context

       fun getNetwork():WeatherApi

       fun getDataBase():WeatherDatabase

}