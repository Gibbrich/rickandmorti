package com.github.gibbrich.rickandmorti.di

object DI {
    lateinit var appComponent: AppComponent
        private set

    fun init(appComponent: AppComponent) {
        this.appComponent = appComponent
    }
}