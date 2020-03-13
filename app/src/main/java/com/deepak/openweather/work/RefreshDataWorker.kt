package com.deepak.openweather.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.WeatherDatabase
import com.deepak.openweather.data.network.WeatherApi
import retrofit2.HttpException
import javax.inject.Inject

class RefreshDataWorker(context: Context, parameters: WorkerParameters, val weatherRepository: WeatherRepository) :
    CoroutineWorker(context, parameters) {

    /**
     * Creator class - customworkerfactorycreator class that creates workercreator, and provide context, parameters
     *  to workercreator through constructor injection
     *
     *  Creator class creates workerclass and gives repository as dependency
     *  WorkerCreator's create() refreshDataWorker class and dependencies are given
     */

    class Creator @Inject constructor(val weatherRepository: WeatherRepository) :
    WorkerCreator {

        override fun create(context: Context, workerParameters: WorkerParameters): CoroutineWorker  =
            RefreshDataWorker(context, workerParameters, weatherRepository)
    }


    private val TAG = "RefreshWorker"

    companion object {
        val WORK_NAME = "com.deepak.openweather.work.RefreshDataWorker"

    }

    override suspend fun doWork(): Result {

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