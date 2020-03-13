package com.deepak.openweather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.deepak.openweather.data.WeatherProperty


@Database(entities = [WeatherProperty::class], version = 2)
@TypeConverters(Convertor::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}

private lateinit var instance: WeatherDatabase

fun dataBase(context: Context): WeatherDatabase {

    synchronized(WeatherDatabase::class.java) {

        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(context, WeatherDatabase::class.java, "weather")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    return instance
}

     val MIGRATION_1_2 = object : Migration(1,2) {

         override fun migrate(database: SupportSQLiteDatabase) {
             database.execSQL("ALTER TABLE 'weatherproperty' ADD COLUMN 'wind' TEXT ")
         }
     }
