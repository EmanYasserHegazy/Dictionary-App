package com.user.dictionaryapplication.domain.use_case

import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.repository.WordRepository
import com.user.dictionaryapplication.domain.util.Result
import javax.inject.Inject

class WordDefinitionUseCase @Inject constructor(private val wordRepository: WordRepository) {

    suspend operator fun invoke(word: String): Result<Word> {
        return wordRepository.getWordDefinition(word)
    }
}