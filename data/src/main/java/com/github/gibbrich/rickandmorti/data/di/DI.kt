package com.github.gibbrich.rickandmorti.data.di

import com.github.gibbrich.rickandmorti.core.di.DI as CoreDI


object DI {
    val dataComponent: DataComponent by lazy {
        DaggerDataComponent
            .builder()
            .coreComponent(CoreDI.coreComponent)
            .build()
    }
}