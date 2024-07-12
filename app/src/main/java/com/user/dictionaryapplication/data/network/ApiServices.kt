package com.user.dictionaryapplication.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("entries/en/{word}")
    suspend fun searchInDictionary(@Path("word") word: String): Response<List<WordResponse>>?
}
