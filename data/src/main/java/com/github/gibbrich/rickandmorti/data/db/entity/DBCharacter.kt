package com.github.gibbrich.rickandmorti.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class DBCharacter(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "first_episode")
    val firstEpisode: Int,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String
)