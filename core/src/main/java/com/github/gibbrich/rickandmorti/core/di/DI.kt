package com.github.gibbrich.rickandmorti.core.di

import android.app.Application
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager

object DI {
    private lateinit var application: Application
    private lateinit var navigationManager: INavigationManager

    fun init(
        application: Application,
        navigationManager: INavigationManager
    ) {
        DI.application = application
        DI.navigationManager = navigationManager
    }

    val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .coreModule(CoreModule(application, navigationManager))
            .build()
    }
}