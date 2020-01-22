package com.github.gibbrich.rickandmorti

import com.github.gibbrich.rickandmorti.di.AppComponent
import com.github.gibbrich.rickandmorti.di.AppModuleMock
import com.github.gibbrich.rickandmorti.di.DaggerAppComponentMock

class AppTest : App() {
    override fun provideAppComponent(): AppComponent {
        return DaggerAppComponentMock.builder()
            .appModuleMock(AppModuleMock())
            .build()
    }
}