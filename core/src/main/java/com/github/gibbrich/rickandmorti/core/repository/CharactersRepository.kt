package com.github.gibbrich.rickandmorti.core.repository

import androidx.lifecycle.LiveData
import com.github.gibbrich.rickandmorti.core.model.Character

/**
 * Characters source. Retrieve data from local and remote sources.
 */
interface CharactersRepository {

    /**
     * Fetches all characters from local DB, sorted by [Character.firstEpisode]
     */
    val characters: LiveData<List<Character>>

    /**
     * Fetches additional characters chunk from server and saves to local DB.
     */
    suspend fun fetchCharacters()
}