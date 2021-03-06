package com.deepak.openweather.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class CustomWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out CoroutineWorker>, @JvmSuppressWildcards Provider<WorkerCreator>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker? {
        val foundEntry =  workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}

interface WorkerCreator {
    fun create(context: Context, workerParameters: WorkerParameters) : CoroutineWorker
}