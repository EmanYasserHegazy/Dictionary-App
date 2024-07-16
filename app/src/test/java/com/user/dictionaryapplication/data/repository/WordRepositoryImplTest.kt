package com.user.dictionaryapplication.data.repository

import com.user.dictionaryapplication.data.cache.WordEntity
import com.user.dictionaryapplication.data.error_handling.ApiExceptions
import com.user.dictionaryapplication.data.error_handling.ErrorHandlerStrategy
import com.user.dictionaryapplication.data.error_handling.NetworkExceptions
import com.user.dictionaryapplication.data.network.Definition
import com.user.dictionaryapplication.data.network.Meaning
import com.user.dictionaryapplication.data.network.WordResponse
import com.user.dictionaryapplication.data.source.local.WordLocalDataSource
import com.user.dictionaryapplication.data.source.remote.WordRemoteDataSource
import com.user.dictionaryapplication.domain.util.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response

class WordRepositoryImplTest {

    private lateinit var wordRepositoryImpl: WordRepositoryImpl
    private lateinit var wordLocalDataSource: WordLocalDataSource
    private lateinit var wordRemoteDataSource: WordRemoteDataSource
    private lateinit var errorHandler: ErrorHandlerStrategy

    @Before
    fun setUp() {
        wordLocalDataSource = mockk()
        wordRemoteDataSource = mockk()
        errorHandler = mockk()

        wordRepositoryImpl =
            WordRepositoryImpl(wordLocalDataSource, wordRemoteDataSource, errorHandler)
    }

    @Test
    fun `test get Word Definition should return from remote`() = runTest {
        val wordResponseList = listOf(
            WordResponse(
                word = "test", meanings = listOf(
                    Meaning(
                        definitions = listOf(Definition(definition = "test definition", "")),
                        partOfSpeech = ""
                    )
                ), phonetic = "", phonetics = emptyList(), origin = ""
            )
        )
        val successResponse = Response.success(wordResponseList)



        coEvery { wordLocalDataSource.getWordFromLocalStorage(anyString()) } returns null
        coEvery { wordRemoteDataSource.getWordDefinition(anyString()) } returns successResponse
        coEvery { errorHandler.handleGenericException(null, "") } returns Result.Error(
            data = null, ""
        )
        coEvery { wordLocalDataSource.insertWordLocally(any()) } just runs

        val result = wordRepositoryImpl.getWordDefinition(anyString())

        val actualDefinition = (result as Result.Success).data?.definition
        val expectedDefinition =
            successResponse.body()?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition

        assert(result is Result.Success)
        assertEquals(
            expectedDefinition,
            actualDefinition
        )
    }


    @Test
    fun `test get Word Definition should return from local database`() = runTest {
        val expectedWordEntity = WordEntity(1, "book", "something to read from")
        coEvery { wordLocalDataSource.getWordFromLocalStorage("book") } returns expectedWordEntity

        val result = wordRepositoryImpl.getWordDefinition("book")

        val expectedDefinition = expectedWordEntity.definition
        val actualDefinition = (result as Result.Success).data?.definition

        assert(result is Result.Success)
        assertEquals(expectedDefinition, actualDefinition)
    }

    @Test
    fun `test get Word Definition should return error result with network exception`() = runTest {
        val networkExceptions = NetworkExceptions("network exceptions")
        val expectedResult = Result.Error(data = null, networkExceptions.message ?: "")

        coEvery {
            errorHandler.handleNetworkException(
                networkExceptions
            )
        } returns expectedResult

        coEvery { wordLocalDataSource.getWordFromLocalStorage(anyString()) } throws networkExceptions

        val result = wordRepositoryImpl.getWordDefinition(anyString())


        assert(result is Result.Error)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `test get Word Definition should return error result with api exception`() = runTest {
        val apiExceptions = ApiExceptions("api exceptions")
        val expectedResult = Result.Error(data = null, apiExceptions.message ?: "")

        coEvery {
            errorHandler.handleApiException(
                apiExceptions, any()
            )
        } returns expectedResult

        coEvery { wordLocalDataSource.getWordFromLocalStorage(anyString()) } throws apiExceptions

        val result = wordRepositoryImpl.getWordDefinition(anyString())


        assert(result is Result.Error)
        assertEquals(expectedResult, result)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}