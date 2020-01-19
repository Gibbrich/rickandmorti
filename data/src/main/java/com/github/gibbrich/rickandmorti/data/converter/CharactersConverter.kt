package com.github.gibbrich.rickandmorti.data.converter

import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.data.api.model.NWCharacter
import com.github.gibbrich.rickandmorti.data.api.model.NWCharactersResponse
import com.github.gibbrich.rickandmorti.data.api.model.getFirstEpisode

object CharactersConverter {
    fun fromDB(data: DBCharacter): Character = Character(
        data.id,
        data.name,
        data.firstEpisode,
        data.photoUrl
    )

    fun toDB(data: Character): DBCharacter = DBCharacter(
        data.id,
        data.name,
        data.firstEpisode,
        data.photoUrl
    )

    fun fromNetwork(data: NWCharactersResponse): List<Character> = data.results.map(this::fromNetwork)

    private fun fromNetwork(data: NWCharacter): Character = Character(
        data.id,
        data.name,
        data.getFirstEpisode(),
        data.photoUrl
    )
}