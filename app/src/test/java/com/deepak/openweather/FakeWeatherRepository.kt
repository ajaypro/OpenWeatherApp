package com.deepak.openweather

import androidx.lifecycle.MutableLiveData
import com.deepak.openweather.data.DefaultRepository
import com.deepak.openweather.data.WeatherProperty

class FakeWeatherRepository : DefaultRepository {

    val weatherSerivceData: LinkedHashMap<String, WeatherProperty> = LinkedHashMap()

    private val observeWeather = MutableLiveData<WeatherProperty>()

    override suspend fun refreshData(array: DoubleArray?) {



    }
}