package com.github.gibbrich.rickandmorti.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

@Dao
interface CharactersDao {
    @Query("SELECT id, name, first_episode, photo_url FROM character ORDER BY first_episode DESC")
    fun getCharacters(): LiveData<List<DBCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<DBCharacter>)

    @Query("SELECT count(*) FROM character WHERE id=:id")
    suspend fun isCharacterInDB(id: Int): Boolean
}