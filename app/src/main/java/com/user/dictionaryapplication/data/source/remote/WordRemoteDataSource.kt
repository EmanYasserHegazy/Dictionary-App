package com.user.dictionaryapplication.data.source.remote

import com.user.dictionaryapplication.data.network.ApiServices
import com.user.dictionaryapplication.data.network.WordResponse
import retrofit2.Response
import javax.inject.Inject

class WordRemoteDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getWordDefinition(word: String): Response<List<WordResponse>>? {
        return apiServices.searchInDictionary(word)
    }

}
