package com.deepak.openweather

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.deepak.openweather.di.component.AppComponent
import com.deepak.openweather.di.modules.ApplicationModule
import com.deepak.openweather.work.CustomWorkerFactory
import javax.inject.Inject

class WeatherApplication: Application() {

    lateinit var applicationComponent: AppComponent

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())
    }

    fun injectDependencies(){

        applicationComponent =  DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setComponent(applicationComponent: AppComponent) {
        this.applicationComponent = applicationComponent
    }
}