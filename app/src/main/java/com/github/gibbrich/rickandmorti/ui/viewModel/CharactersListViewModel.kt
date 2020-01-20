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
    private val charactersSource = MutableLiveData<List<Character>?>(null)
    val characters: LiveData<List<Character>?> = charactersSource

    private val loadingSource = MutableLiveData(false)
    val loading: LiveData<Boolean> = loadingSource

    private val errorSource: MutableLiveData<Boolean?> = MutableLiveData(false)
    val error: LiveData<Boolean?> = errorSource

    @Inject
    internal lateinit var charactersRepository: CharactersRepository

    private var isFirstPhotosLoad = true

    init {
        DI.appComponent.inject(this)
    }

    fun fetchCharacters() {
        loadingSource.value = true
        viewModelScope.launch {
            try {
                val characters = charactersRepository.getCharacters(isFirstPhotosLoad.not())

                if (isFirstPhotosLoad) {
                    isFirstPhotosLoad = false
                }

                charactersSource.value = characters
            } catch (e: Exception) {
                e.printStackTrace()
                errorSource.value = true
                errorSource.value = null
            } finally {
                loadingSource.value = false
            }
        }
    }

    sealed class State {
        object Empty: State()
        data class Loaded(val characters: List<Character>): State()
    }
}
