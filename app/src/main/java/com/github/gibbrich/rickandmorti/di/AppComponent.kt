package com.github.gibbrich.rickandmorti.di

import com.github.gibbrich.rickandmorti.data.di.DataComponent
import com.github.gibbrich.rickandmorti.ui.activity.MainActivity
import com.github.gibbrich.rickandmorti.ui.fragment.CharacterListFragment
import com.github.gibbrich.rickandmorti.ui.viewModel.CharactersListViewModel
import dagger.Component

@AppScope
@Component(
    dependencies = [
        DataComponent::class
    ]
)
interface AppComponent {
    fun inject(entry: MainActivity)
    fun inject(entry: CharactersListViewModel)
    fun inject(entry: CharacterListFragment)
}