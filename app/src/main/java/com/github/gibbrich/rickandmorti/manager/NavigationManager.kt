package com.github.gibbrich.rickandmorti.manager

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.ui.fragment.CharacterDetailFragment

class NavigationManager : INavigationManager {
    var navController: NavController? = null

    override fun switchToCharacterDetailScreen(
        characterImage: View,
        character: Character
    ) {
        val extras = FragmentNavigatorExtras(characterImage to character.photoUrl)

        navController?.navigate(
            R.id.action_characterListFragment_to_characterDetailFragment,
            CharacterDetailFragment.getParams(character),
            null,
            extras
        )
    }
}