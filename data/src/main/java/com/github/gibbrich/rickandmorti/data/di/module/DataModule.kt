package com.github.gibbrich.rickandmorti.data.di.module

import com.github.gibbrich.rickandmorti.data.api.Api
import com.github.gibbrich.rickandmorti.data.db.AppDatabase
import com.github.gibbrich.rickandmorti.data.di.DataScope
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.core.repository.PreferencesRepository
import com.github.gibbrich.rickandmorti.data.repository.CharactersDataRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @DataScope
    fun provideCharactersRepository(
        api: Api,
        db: AppDatabase,
        preferencesRepository: PreferencesRepository
    ): CharactersRepository = CharactersDataRepository(api, db, preferencesRepository)
}