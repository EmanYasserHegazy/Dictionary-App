package com.user.dictionaryapplication.data.error_handling

import java.io.IOException
import javax.inject.Inject
import com.user.dictionaryapplication.domain.util.Result


class ErrorHandlerStrategyImpl @Inject constructor() : ErrorHandlerStrategy {
    override fun handleNetworkException(
        e: NetworkExceptions?,
        message: String?
    ): Result.Error<Nothing> {
        message?.let { return Result.Error(message = " $message") }
            ?: return Result.Error(message = "${e?.localizedMessage}")
    }

    override fun handleApiException(e: ApiExceptions?, message: String?): Result.Error<Nothing> {
        message?.let { return Result.Error(message = " $message") } ?: return Result.Error(
            message = "${e?.localizedMessage}"
        )
    }

    override fun handleIOException(e: IOException?, message: String?): Result.Error<Nothing> {
        message?.let { return Result.Error(message = " $message") }
            ?: return Result.Error(message = "${e?.localizedMessage}")
    }

    override fun handleGenericException(e: Exception?, message: String?): Result.Error<Nothing> {
        message?.let { return Result.Error(message = " $message") }
            ?: return Result.Error(message = "${e?.localizedMessage}")
    }
}