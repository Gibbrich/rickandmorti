package com.github.gibbrich.rickandmorti.data.api.model

import com.google.gson.annotations.SerializedName

data class NWEpisodeResponse(
    @SerializedName("id")
    val id: Int,

    /**
     * List of URLs in format "https://rickandmortyapi.com/api/character/42"
     */
    @SerializedName("characters")
    val characters: List<String>
)

fun NWEpisodeResponse.getCharacterIds(): List<Int> = characters.map { it.substringAfter("character/").toInt() }