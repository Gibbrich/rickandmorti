package com.github.gibbrich.rickandmorti.data.api

import com.github.gibbrich.rickandmorti.data.api.model.NWCharacter
import com.github.gibbrich.rickandmorti.data.api.model.NWEpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("character/{ids}")
    suspend fun getCharacters(@Path("ids") characters: List<Int>): List<NWCharacter>

    @GET("episode/{episode}")
    suspend fun getEpisode(@Path("episode") episode: Int): NWEpisodeResponse
}