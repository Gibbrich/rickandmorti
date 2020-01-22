package com.github.gibbrich.rickandmorti.di

import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class AppModuleMock {
    @Singleton
    @Provides
    fun provideCharactersRepository(): CharactersRepository = Mockito.mock(CharactersRepository::class.java)

    @Singleton
    @Provides
    fun provideNavigationManager(): INavigationManager = Mockito.mock(INavigationManager::class.java)
}