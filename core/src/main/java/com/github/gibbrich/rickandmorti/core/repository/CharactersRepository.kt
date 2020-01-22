package com.github.gibbrich.rickandmorti.core.repository

import com.github.gibbrich.rickandmorti.core.model.Character

/**
 * Characters source. Retrieve data from local and remote sources.
 */
interface CharactersRepository {

    /**
     * Fetches additional characters chunk from server and saves to local DB.
     */
    suspend fun fetchNextCharacters(limit: Int, start: Int): List<Character>
}