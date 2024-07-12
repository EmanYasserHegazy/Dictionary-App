package com.user.dictionaryapplication.domain.repository

import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.util.Result

interface WordRepository {

    suspend fun getWordDefinition(wordText: String): Result<Word>
    suspend fun getPreviousSearchList(): Result<List<Word>?>
}