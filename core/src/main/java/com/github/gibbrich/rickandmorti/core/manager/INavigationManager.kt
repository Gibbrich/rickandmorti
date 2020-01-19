package com.github.gibbrich.rickandmorti.core.manager

import android.view.View
import com.github.gibbrich.rickandmorti.core.model.Character

interface INavigationManager {
    fun switchToCharacterDetailScreen(characterImage: View, character: Character)
}