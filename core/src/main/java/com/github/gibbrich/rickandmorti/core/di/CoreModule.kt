package com.github.gibbrich.rickandmorti.core.di

import android.content.Context
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.repository.PreferencesDataRepository
import com.github.gibbrich.rickandmorti.core.repository.PreferencesRepository
import dagger.Module
import dagger.Provides

@Module
class CoreModule(
    private val context: Context,
    private val navigationManager: INavigationManager
) {

    @Provides
    @CoreScope
    fun provideContext(): Context = context

    @Provides
    @CoreScope
    fun provideNavigationManager(): INavigationManager = navigationManager

    @Provides
    @CoreScope
    fun providePreferencesRepository(): PreferencesRepository = PreferencesDataRepository(context.getSharedPreferences("preferences", Context.MODE_PRIVATE))
}