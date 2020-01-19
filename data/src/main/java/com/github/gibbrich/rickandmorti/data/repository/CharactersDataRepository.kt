package com.github.gibbrich.rickandmorti.data.repository

import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.core.repository.PreferencesRepository
import com.github.gibbrich.rickandmorti.data.api.Api
import com.github.gibbrich.rickandmorti.data.converter.CharactersConverter
import com.github.gibbrich.rickandmorti.data.db.AppDatabase

class CharactersDataRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val preferencesRepository: PreferencesRepository
): CharactersRepository {
    private val cachedCharacters: MutableList<Character> = mutableListOf()

    private var pagesOnServer: Int? = null

    override suspend fun getCharacters(isCacheDirty: Boolean): List<Character> = when {
        shouldReturnCachedData(isCacheDirty) -> {
            cachedCharacters
        }

        else -> {
            getAndCacheLocalCharacters()
        }
    }

    private suspend fun getAndCacheLocalCharacters(): List<Character> {
        val characters = db.charactersDao()
            .getCharacters()
            .map(CharactersConverter::fromDB)

        val sortedCache = cachedCharacters.sortedBy(Character::firstEpisode)
        return if (characters.isEmpty() || characters == sortedCache) {
            getAndSaveRemoteCharacters()
        } else {
            updateCache(characters)
            cachedCharacters
        }
    }

    private suspend fun getAndSaveRemoteCharacters(): List<Character> {
        val page = preferencesRepository.getPagesCached() + 1

        val response = api.getCharacters(page)

        pagesOnServer = response.meta.pages

        val characters = response
            .let(CharactersConverter::fromNetwork)
            .sortedBy(Character::firstEpisode)

        updateCache(characters)

        val dbCharacters = characters.map(CharactersConverter::toDB)
        db.charactersDao().insert(dbCharacters)

        preferencesRepository.updatePagesCached()

        return cachedCharacters
    }

    private fun updateCache(characters: List<Character>) {
        cachedCharacters.addAll(0, characters)
    }

    private fun shouldReturnCachedData(isCacheDirty: Boolean): Boolean =
        cachedCharacters.isNotEmpty() && isCacheDirty.not() || pagesOnServer == preferencesRepository.getPagesCached()
}