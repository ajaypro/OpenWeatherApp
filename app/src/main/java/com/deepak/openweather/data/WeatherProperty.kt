package com.deepak.openweather.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


data class Weather(
    @Json(name = "description") val description: String,
    val icon: String

){
    constructor(): this("", "")
}

data class Main(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "temp_min") val minTemperature: Double,
    @Json(name = "temp_max") val maxTemperature: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
){
    constructor(): this(0.0, 0.0, 0.0, 0, 0)
}
data class Wind(@Json(name = "speed") val speed: Float)
{
    constructor(): this(0.0F)
}


@Entity
data class WeatherProperty(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val weather: List<Weather>,
    val main: Main?,
    val wind: Wind?,
    @Json(name = "dt") val utcTime: Long,
    @Json(name = "name") val name: String
){
    constructor(): this(0, emptyList<Weather>(), null,null, 0,"")
}
/*
 * The forecast endpoint does not return array, instead an object which contains "list" array.
 * Therefore, we need this approach.
 */



