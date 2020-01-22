package com.github.gibbrich.rickandmorti

import android.app.Application
import com.github.gibbrich.rickandmorti.manager.NavigationManager
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.data.di.DI as DataDI
import com.github.gibbrich.rickandmorti.core.di.DI as CoreDI
import com.github.gibbrich.rickandmorti.di.DI
import com.github.gibbrich.rickandmorti.di.AppComponent
import com.github.gibbrich.rickandmorti.di.DaggerAppComponent

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CoreDI.init(this, provideNavigationManager())
        DI.init(provideAppComponent())
    }

    private fun provideNavigationManager(): INavigationManager = NavigationManager()

    /**
     * Override for implementing UI tests
     */
    open fun provideAppComponent(): AppComponent = DaggerAppComponent
        .builder()
        .dataComponent(DataDI.dataComponent)
        .build()
}