package com.github.gibbrich.rickandmorti.core.repository

import android.content.SharedPreferences

interface PreferencesRepository {
    fun getPagesCached(): Int
    fun updatePagesCached()
}

class PreferencesDataRepository(
    private val sharedPreferences: SharedPreferences
): PreferencesRepository {
    companion object {
        private const val PAGES_CACHED_QTY = "PAGES_CACHED_QTY"
    }

    override fun getPagesCached(): Int = sharedPreferences.getInt(PAGES_CACHED_QTY, 0)

    override fun updatePagesCached() {
        val pagesCached = getPagesCached()
        sharedPreferences.edit().putInt(PAGES_CACHED_QTY, pagesCached + 1).apply()
    }
}