package com.user.dictionaryapplication.data.error_handling

import java.io.IOException
import com.user.dictionaryapplication.domain.util.Result

interface ErrorHandlerStrategy {
    fun handleNetworkException(
        e: NetworkExceptions? = null,
        message: String? = null
    ): Result.Error<Nothing>

    fun handleApiException(e: ApiExceptions? = null, message: String? = null): Result.Error<Nothing>
    fun handleIOException(e: IOException? = null, message: String? = null): Result.Error<Nothing>
    fun handleGenericException(e: Exception? = null, message: String? = null): Result.Error<Nothing>
}