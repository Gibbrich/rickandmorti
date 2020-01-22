package com.github.gibbrich.rickandmorti.di

import com.github.gibbrich.rickandmorti.ui.viewModel.CharactersListViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleMock::class
])
interface AppComponentMock: AppComponent {
    fun inject(entry: CharactersListViewModelTest)
}