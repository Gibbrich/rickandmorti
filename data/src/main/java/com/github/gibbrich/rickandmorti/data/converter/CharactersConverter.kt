package com.github.gibbrich.rickandmorti.data.converter

import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.data.api.model.NWCharacter
import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

object CharactersConverter {
    fun fromDB(data: DBCharacter): Character = Character(
        data.id,
        data.name,
        data.firstEpisode,
        data.photoUrl,
        data.origin
    )

    fun toDB(data: Character): DBCharacter = DBCharacter(
        data.id,
        data.name,
        data.firstEpisode,
        data.origin,
        data.photoUrl
    )

    fun fromNetwork(
        data: NWCharacter,
        episode: String
    ): Character = Character(
        data.id,
        data.name,
        episode,
        data.photoUrl,
        data.origin.name
    )
}