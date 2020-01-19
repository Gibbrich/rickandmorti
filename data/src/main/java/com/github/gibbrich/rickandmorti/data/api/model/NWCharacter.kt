package com.github.gibbrich.rickandmorti.data.api.model

import com.google.gson.annotations.SerializedName

class NWCharacter(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    /**
     * List of URLs in format "https://rickandmortyapi.com/api/episode/42"
     */
    @SerializedName("episode")
    val episodes: List<String>,

    @SerializedName("image")
    val photoUrl: String
)

fun NWCharacter.getFirstEpisode(): Int = episodes
    .map {
        it.substringAfter("episode/").toInt()
    }
    .min()!!