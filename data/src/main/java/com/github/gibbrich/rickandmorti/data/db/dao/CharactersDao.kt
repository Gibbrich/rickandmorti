package com.github.gibbrich.rickandmorti.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

@Dao
interface CharactersDao {
    @Query("SELECT id, name, first_episode, photo_url FROM character ORDER BY first_episode")
    suspend fun getCharacters(): List<DBCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<DBCharacter>)
}