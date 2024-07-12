package com.user.dictionaryapplication.data.di

import com.user.dictionaryapplication.data.Constants.BASE_URL
import com.user.dictionaryapplication.data.Constants.TIMEOUT_CONNECT
import com.user.dictionaryapplication.data.Constants.TIMEOUT_READ
import com.user.dictionaryapplication.data.network.ApiServices
import com.user.dictionaryapplication.data.source.remote.WordRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        return okHttpClient.build()
    }


    @Singleton
    @Provides
    fun provideGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(gsonFactory)
            .client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }


    @Provides
    fun provideRemoteDataSource(apiServices: ApiServices): WordRemoteDataSource {
        return WordRemoteDataSource(apiServices)
    }
}