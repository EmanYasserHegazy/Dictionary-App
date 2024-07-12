package com.user.dictionaryapplication.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryAppDao {

    @Query("SELECT * FROM words")
    suspend fun getAllWordList(): List<WordEntity>?

    @Query("SELECT * FROM words WHERE word = :word")
    suspend fun getWord(word: String): WordEntity?

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertWord(word: WordEntity)
}