package com.github.gibbrich.rickandmorti.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.gibbrich.rickandmorti.data.db.dao.CharactersDao
import com.github.gibbrich.rickandmorti.data.db.entity.DBCharacter

@Database(entities = [
    DBCharacter::class
], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}