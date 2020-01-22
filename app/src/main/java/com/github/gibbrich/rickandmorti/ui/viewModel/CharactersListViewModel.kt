package com.github.gibbrich.rickandmorti.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.di.DI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class CharactersListViewModel : ViewModel() {
    companion object {
        private const val CHARACTERS_IN_PAGE = 20
    }

    @Inject
    internal lateinit var charactersRepository: CharactersRepository

    private val characters = mutableListOf<Character>()
    val charactersCached: List<Character> = characters

    private val charactersSource = MutableLiveData<List<Character>>(emptyList())
    val charactersPage: LiveData<List<Character>> = charactersSource

    private val stateSource = MutableLiveData<LoadingState?>()
    val loadingState: LiveData<LoadingState?> = stateSource

    init {
        DI.appComponent.inject(this)

        fetchCharactersPage()
    }

    fun fetchCharactersPage() {
        if (stateSource.value == LoadingState.LOADING) {
            return
        }

        stateSource.value = LoadingState.LOADING
        viewModelScope.launch {
            val result = try {
                val page = charactersRepository.fetchNextCharacters(CHARACTERS_IN_PAGE, characters.size)
                stateSource.value = null

                page
            } catch (e: Exception) {
                e.printStackTrace()
                stateSource.value = LoadingState.ERROR

                emptyList<Character>()
            }

            characters += result

            charactersSource.value = result
            charactersSource.value = emptyList()
        }
    }
}

enum class LoadingState {
    LOADING, ERROR
}
