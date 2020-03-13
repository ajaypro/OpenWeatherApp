package com.deepak.openweather.data.network

import com.deepak.openweather.BuildConfig
import com.deepak.openweather.data.WeatherProperty
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


interface WeatherApi {

    @GET("weather")
      fun getWeatherAsync(
        @Query("lat")lat: Double,
        @Query("lon")lon:Double,
        @Query(Networking.APPID) apiKey: String = Networking.API_KEY): Deferred<WeatherProperty>
}