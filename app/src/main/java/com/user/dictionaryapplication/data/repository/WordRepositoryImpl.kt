package com.user.dictionaryapplication.data.repository

import com.user.dictionaryapplication.data.Constants.API_EXCEPTION_ERROR
import com.user.dictionaryapplication.data.Constants.GENERAL_EXCEPTION_ERROR
import com.user.dictionaryapplication.data.Constants.IO_EXCEPTION_ERROR
import com.user.dictionaryapplication.data.cache.WordEntity
import com.user.dictionaryapplication.data.error_handling.ApiExceptions
import com.user.dictionaryapplication.data.error_handling.ErrorCodes
import com.user.dictionaryapplication.data.error_handling.ErrorHandlerStrategy
import com.user.dictionaryapplication.data.error_handling.NetworkExceptions
import com.user.dictionaryapplication.data.source.local.WordLocalDataSource
import com.user.dictionaryapplication.data.source.remote.WordRemoteDataSource
import com.user.dictionaryapplication.data.source.toWordList
import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.repository.WordRepository
import com.user.dictionaryapplication.domain.util.Result
import java.io.IOException
import javax.inject.Inject


class WordRepositoryImpl @Inject constructor(
    private val wordLocalDataSource: WordLocalDataSource,
    private val wordRemoteDataSource: WordRemoteDataSource,
    private val errorHandler: ErrorHandlerStrategy
) : WordRepository {

    override suspend fun getWordDefinition(wordText: String): Result<Word> {
        try {
            val localCachedWord = wordLocalDataSource.getWordFromLocalStorage(wordText)
            if (localCachedWord != null) {
                return Result.Success(Word(localCachedWord.word, localCachedWord.definition))
            } else {
                val response = wordRemoteDataSource.getWordDefinition(wordText)
                if (response != null && response.isSuccessful) {
                    val wordResponse = response.body()
                    return if (!wordResponse.isNullOrEmpty()) {
                        val searchResult = wordResponse[0]
                        wordLocalDataSource.insertWordLocally(
                            WordEntity(
                                word = searchResult.word,
                                definition = searchResult.meanings[0].definitions[0].definition
                            )
                        )

                        Result.Success(
                            Word(
                                searchResult.word,
                                searchResult.meanings[0].definitions[0].definition
                            )
                        )
                    } else {

                        Result.Error(message = "No Definitions Found")
                    }
                } else {
                    when (response?.code()) {
                        ErrorCodes.BAD_REQUEST.code -> throw ApiExceptions("Bad Request: ${response.message()}")
                        ErrorCodes.SERVICE_NOT_FOUND.code -> throw ApiExceptions("Service Not Found: ${response.message()}")
                        ErrorCodes.INTERNAL_SERVER_ERROR.code -> throw ApiExceptions("Internal Server Error: ${response.message()}")
                        else -> throw ApiExceptions("Error: ${response?.code()} ${response?.message()}")
                    }
                }
            }
        } catch (e: NetworkExceptions) {
            return errorHandler.handleNetworkException(e)
        } catch (e: ApiExceptions) {
            return errorHandler.handleApiException(e, API_EXCEPTION_ERROR)
        } catch (e: IOException) {
            return errorHandler.handleIOException(e, IO_EXCEPTION_ERROR)
        } catch (e: Exception) {
            return errorHandler.handleGenericException(e, GENERAL_EXCEPTION_ERROR)
        }
    }

    override suspend fun getPreviousSearchList(): Result<List<Word>?> {
        return try {
            Result.Success(wordLocalDataSource.getAllPreviousSearchList()?.toWordList())
        } catch (e: Exception) {
            errorHandler.handleGenericException(e, GENERAL_EXCEPTION_ERROR)
        }

    }

}
