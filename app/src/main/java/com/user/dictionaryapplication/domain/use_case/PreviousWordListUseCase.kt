package com.user.dictionaryapplication.domain.use_case

import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.repository.WordRepository
import com.user.dictionaryapplication.domain.util.Result
import javax.inject.Inject

class PreviousWordListUseCase @Inject constructor(private val wordRepository: WordRepository) {
    suspend operator fun invoke(): Result<List<Word>?> = wordRepository.getPreviousSearchList()
}