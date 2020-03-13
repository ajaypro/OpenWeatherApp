package com.deepak.openweather.data.local

import androidx.room.TypeConverter
import com.deepak.openweather.data.Main
import com.deepak.openweather.data.Weather
import com.deepak.openweather.data.Wind
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class Convertor {

     @TypeConverter
    fun fromWeatherList(weather: List<Weather>): String{
         val json = GsonBuilder().create().toJson(weather)
            return json?: ""
     }

    @TypeConverter
    fun fromStringToWeatherList(value: String): List<Weather>{
        val typeToken = object: TypeToken<List<Weather>>(){}.type
        val list = GsonBuilder().create().fromJson<List<Weather>>(value, typeToken)
        return list?: emptyList()
    }
    @TypeConverter
    fun fromMain(main: Main): String{
        val json = GsonBuilder().create().toJson(main)
        return json?:""
    }
    @TypeConverter
    fun fromStringToMain(value: String): Main{
        val typeToken = object : TypeToken<Main>(){}.type
        val list = GsonBuilder().create().fromJson<Main>(value, typeToken)
        return list?: Main()
    }

    @TypeConverter
    fun fromWind(wind: Wind): String {
        val json = GsonBuilder().create().toJson(wind)
        return json?:""
    }
    @TypeConverter
    fun fromStringToWind(value: String?): Wind{
        val typeToken = object : TypeToken<Wind>(){}.type
        val list = GsonBuilder().create().fromJson<Wind>(value?.let { it }, typeToken)
        return list?: Wind()
    }

 }