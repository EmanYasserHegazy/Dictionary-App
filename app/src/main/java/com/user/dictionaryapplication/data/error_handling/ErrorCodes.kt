package com.user.dictionaryapplication.data.error_handling

enum class ErrorCodes(val code: Int) {
    BAD_REQUEST(400),
    SERVICE_NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500)
}