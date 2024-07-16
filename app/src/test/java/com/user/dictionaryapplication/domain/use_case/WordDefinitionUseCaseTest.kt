package com.user.dictionaryapplication.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.repository.WordRepository
import com.user.dictionaryapplication.domain.util.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class WordDefinitionUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var wordRepository: WordRepository
    private lateinit var wordDefinitionUseCase: WordDefinitionUseCase


    @Before
    fun setUp() {
        wordRepository = mockk()
        wordDefinitionUseCase = WordDefinitionUseCase(wordRepository)
    }

    @Test
    fun `test when call get word definition return success result with word definition`() =
        runTest {
            val expectedWord = Word("eman", "")
            val expectedResult = Result.Success(expectedWord)

            coEvery { wordRepository.getWordDefinition(any()) } returns expectedResult

            val result = wordDefinitionUseCase.invoke("eman")

            assert(result is Result.Success)
        }


    @Test
    fun `test when call get word definition return error result with word definition`() =
        runTest {
            val errorMessage = "something went wrong"
            val expectedResult = Result.Error(data = null, errorMessage)

            coEvery { wordRepository.getWordDefinition(any()) } returns expectedResult

            val result = wordDefinitionUseCase.invoke(anyString())

            assert(result is Result.Error)
        }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}