package com.github.gibbrich.rickandmorti.data.di

import com.github.gibbrich.rickandmorti.data.di.module.ApiModule
import com.github.gibbrich.rickandmorti.data.di.module.DBModule
import com.github.gibbrich.rickandmorti.data.di.module.DataModule
import com.github.gibbrich.rickandmorti.core.di.CoreComponent
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import dagger.Component

@DataScope
@Component(
    modules = [
        ApiModule::class,
        DBModule::class,
        DataModule::class
    ],
    dependencies = [
        CoreComponent::class
    ]
)
interface DataComponent {
    val charactersRepository: CharactersRepository
    val navigationManager: INavigationManager
}