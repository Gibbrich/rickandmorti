package com.github.gibbrich.rickandmorti.data.repository

import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.core.repository.PreferencesRepository
import com.github.gibbrich.rickandmorti.data.api.Api
import com.github.gibbrich.rickandmorti.data.api.model.getCharacterIds
import com.github.gibbrich.rickandmorti.data.converter.CharactersConverter
import com.github.gibbrich.rickandmorti.data.db.AppDatabase
import retrofit2.HttpException

/**
 * Places, marked with "note" can be improved in terms of performance by adding
 * in-memory cache.
 */
class CharactersDataRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val preferencesRepository: PreferencesRepository
) : CharactersRepository {
    override suspend fun fetchNextCharacters(limit: Int, start: Int): List<Character> {
        val characters = db.charactersDao()
            .getCharacters(start, limit)
            .map(CharactersConverter::fromDB)
        if (characters.isNotEmpty()) {
            return characters
        }

        val episode = preferencesRepository.getEpisodesCached() + 1

        val episodeResponse = try {
            api.getEpisode(episode)
        } catch (e: Exception) {
            // if we fetch episode with id greater, than in DB on server,
            // error 404 will be returned. In this case just do nothing, otherwise
            // throw error further
            if (e is HttpException && e.code() == 404) {
                return emptyList()
            } else {
                throw e
            }
        }

        val charactersToFetch = episodeResponse
            .getCharacterIds()
            .filter {
                // note - can also be checked in cache for increasing performance
                db.charactersDao().isCharacterInDB(it).not()
            }

        // in case of all characters are fetched, there can be case, that for
        // particular episode we won't fetch anybody. For simplicity we just
        // update episodes fetched and do not update list or notify user.
        val result = if (charactersToFetch.isNotEmpty()) {
            val fetchedCharacters = api.getCharacters(charactersToFetch)
                    .map { CharactersConverter.fromNetwork(it, episodeResponse.episode) }
            val dbCharacters = fetchedCharacters.map(CharactersConverter::toDB)
            db.charactersDao().insert(dbCharacters)
            fetchedCharacters
        } else {
            emptyList()
        }

        preferencesRepository.updateEpisodesCached()

        return result
    }
}