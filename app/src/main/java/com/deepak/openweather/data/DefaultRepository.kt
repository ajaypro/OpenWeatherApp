package com.deepak.openweather.data

interface DefaultRepository {

    suspend fun refreshData(array: DoubleArray?)
}