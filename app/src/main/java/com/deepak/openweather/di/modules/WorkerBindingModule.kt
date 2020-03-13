package com.deepak.openweather.di.modules

import com.deepak.openweather.di.WorkerKey
import com.deepak.openweather.work.RefreshDataWorker
import com.deepak.openweather.work.WorkerCreator
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This module is binding the refershworkerclass to its creator (WorkerCreator) by creating a @mapkey
 *  and linking to creator class
 */

@Module
interface WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshWorker(creator: RefreshDataWorker.Creator): WorkerCreator
}