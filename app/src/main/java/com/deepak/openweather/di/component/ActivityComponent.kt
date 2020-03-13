package com.deepak.openweather.di.component

import com.deepak.openweather.di.ActivityScope
import com.deepak.openweather.di.modules.ActivityModule
import com.deepak.openweather.ui.MainActivity
import com.deepak.openweather.ui.WeatherViewModel
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class],
    modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun getViewModel(): WeatherViewModel
}