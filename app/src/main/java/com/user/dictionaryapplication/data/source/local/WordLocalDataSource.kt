package com.user.dictionaryapplication.data.source.local


import com.user.dictionaryapplication.data.cache.DictionaryAppDao
import com.user.dictionaryapplication.data.cache.WordEntity
import javax.inject.Inject

data class WordLocalDataSource @Inject constructor(private val wordDao: DictionaryAppDao) {
    suspend fun getWordFromLocalStorage(word: String): WordEntity? = wordDao.getWord(word)

    suspend fun insertWordLocally(word: WordEntity) =
        wordDao.insertWord(word)

    suspend fun getAllPreviousSearchList() = wordDao.getAllWordList()
}
