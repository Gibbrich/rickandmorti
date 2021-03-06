package com.github.gibbrich.rickandmorti.core.repository

import android.content.SharedPreferences

interface PreferencesRepository {
    /**
     * @return number of episodes user already fetched from server and cached
     */
    fun getEpisodesCached(): Int

    /**
     * Increments number of episodes fetched from server and cached
     */
    fun updateEpisodesCached()
}

class PreferencesDataRepository(
    private val sharedPreferences: SharedPreferences
): PreferencesRepository {
    companion object {
        private const val EPISODES_CACHED_QTY = "EPISODES_CACHED_QTY"
    }

    override fun getEpisodesCached(): Int = sharedPreferences.getInt(EPISODES_CACHED_QTY, 0)

    override fun updateEpisodesCached() {
        val episodesCached = getEpisodesCached()
        sharedPreferences.edit().putInt(EPISODES_CACHED_QTY, episodesCached + 1).apply()
    }
}