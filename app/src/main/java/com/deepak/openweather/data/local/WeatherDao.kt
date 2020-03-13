package com.deepak.openweather.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepak.openweather.data.WeatherProperty

@Dao
interface WeatherDao {

    @Query("select * from weatherproperty")
    fun get(): LiveData<WeatherProperty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weather: WeatherProperty)

    @Query("delete from weatherproperty")
    fun deleteAll()

}