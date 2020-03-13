package com.deepak.openweather.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.deepak.openweather.BuildConfig
import com.deepak.openweather.WeatherApplication
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.Networking
import com.deepak.openweather.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val weatherApplication: WeatherApplication) {


    @Singleton
    @Provides
     fun provideApplication(): Application = weatherApplication

    @Singleton
    @Provides
     fun provideContext(): Context = weatherApplication

    @Singleton
    @Provides
     fun provideDataBase(): WeatherDatabase =
         Room.databaseBuilder(weatherApplication, WeatherDatabase::class.java,
             "weather")
             .addMigrations(object : Migration(1,2){
                 override fun migrate(database: SupportSQLiteDatabase) {
                     database.execSQL("ALTER TABLE 'weatherproperty' ADD COLUMN 'wind' TEXT ")
                 }
             })
             .build()
    @Singleton
    @Provides
    fun provideNetworkService(): WeatherApi = Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL)


    @Provides
    fun provideWeatherRepository(weatherDatabase: WeatherDatabase, weatherApi: WeatherApi ): WeatherRepository =
        WeatherRepository(weatherDatabase, weatherApi)

}