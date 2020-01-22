package com.github.gibbrich.rickandmorti.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

@Dao
interface CharactersDao {
    /**
     * Fetch characters chunk. Used for pagination.
     */
    @Query("SELECT * FROM character ORDER BY first_episode LIMIT :limit OFFSET :start")
    suspend fun getCharacters(start: Int, limit: Int): List<DBCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<DBCharacter>)

    @Query("SELECT count(*) FROM character WHERE id=:id")
    suspend fun isCharacterInDB(id: Int): Boolean
}