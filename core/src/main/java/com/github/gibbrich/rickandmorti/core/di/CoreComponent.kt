package com.github.gibbrich.rickandmorti.core.di

import android.content.Context
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.repository.PreferencesRepository
import dagger.Component

@CoreScope
@Component(modules = [
    CoreModule::class
])
interface CoreComponent {
    val context: Context
    val navigationManager: INavigationManager
    val preferencesRepository: PreferencesRepository
}