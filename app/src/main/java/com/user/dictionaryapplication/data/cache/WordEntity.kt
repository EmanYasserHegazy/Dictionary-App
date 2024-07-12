package com.user.dictionaryapplication.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo val word: String,
    @ColumnInfo val definition: String,
)
