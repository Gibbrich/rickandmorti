package com.github.gibbrich.rickandmorti.core.repository

import com.github.gibbrich.rickandmorti.core.model.Character

interface CharactersRepository {

    /**
     * Fetches all characters from cache and local db or from remote, sorted by [Character.firstEpisode]
     * @param isCacheDirty if true fetches from db and if cached data == db data, fetches from remote; otherwise tries to fetch from cache, db and remote respectively
     * @return characters, sorted by [Character.firstEpisode]
     */
    suspend fun getCharacters(isCacheDirty: Boolean = false): List<Character>
}