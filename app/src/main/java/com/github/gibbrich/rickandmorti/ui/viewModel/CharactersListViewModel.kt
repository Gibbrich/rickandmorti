package com.github.gibbrich.rickandmorti.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.di.DI
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class CharactersListViewModel : ViewModel() {
    private val state = MutableLiveData<State>(State.Empty)
    val viewState: LiveData<State> = state

    @Inject
    internal lateinit var charactersRepository: CharactersRepository

    private var isFirstPhotosLoad = true

    init {
        DI.appComponent.inject(this)
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            state.value = try {
                val characters = charactersRepository.getCharacters(isFirstPhotosLoad.not())

                if (isFirstPhotosLoad) {
                    isFirstPhotosLoad = false
                }

                State.Loaded(characters)
            } catch (e: Exception) {
                e.printStackTrace()
                State.LoadError
            }
        }
    }

    sealed class State {
        object LoadError: State()
        object Loading: State()
        object Empty: State()
        data class Loaded(val characters: List<Character>): State()
    }
}
