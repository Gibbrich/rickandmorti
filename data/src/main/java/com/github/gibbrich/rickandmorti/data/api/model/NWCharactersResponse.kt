package com.github.gibbrich.rickandmorti.data.api.model

import com.google.gson.annotations.SerializedName

data class NWCharactersResponse(
    @SerializedName("info")
    val meta: NWCharactersInfo,

    @SerializedName("results")
    val results: List<NWCharacter>
)