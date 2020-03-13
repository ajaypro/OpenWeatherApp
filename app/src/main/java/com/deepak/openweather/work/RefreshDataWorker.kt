package com.deepak.openweather.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.dataBase
import retrofit2.HttpException

class RefreshDataWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val TAG = "RefreshWorker"

    companion object {
        val WORK_NAME = "com.deepak.openweather.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {

        val dataBase = dataBase(applicationContext)
        val weatherRepository = WeatherRepository(dataBase, applicationContext)

        try {
            Log.d(TAG, "calling refresh data")
            weatherRepository.refreshData(inputData.getDoubleArray("location"))
            Log.d(TAG, "Work request for sync is run")

        } catch (httpException: HttpException) {
            return Result.retry()
        }
        return Result.success()

    }
}