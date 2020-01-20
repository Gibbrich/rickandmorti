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
    @Inject
    internal lateinit var charactersRepository: CharactersRepository

    val characters: LiveData<List<Character>>

    private val loadingSource = MutableLiveData(false)
    val loading: LiveData<Boolean> = loadingSource

    private val errorSource: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = errorSource

    init {
        DI.appComponent.inject(this)

        characters = charactersRepository.characters
    }

    fun fetchCharacters() {
        loadingSource.value = true
        viewModelScope.launch {
            try {
                charactersRepository.fetchCharacters()
            } catch (e: Exception) {
                e.printStackTrace()
                errorSource.value = true
                errorSource.value = false
            } finally {
                loadingSource.value = false
            }
        }
    }
}
