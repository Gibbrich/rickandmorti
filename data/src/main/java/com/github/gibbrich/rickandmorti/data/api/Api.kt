package com.github.gibbrich.rickandmorti.data.api

import com.github.gibbrich.rickandmorti.data.api.model.NWCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): NWCharactersResponse
}