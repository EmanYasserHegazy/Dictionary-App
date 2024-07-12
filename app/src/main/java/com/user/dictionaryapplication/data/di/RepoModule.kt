package com.user.dictionaryapplication.data.di

import com.user.dictionaryapplication.data.repository.WordRepositoryImpl
import com.user.dictionaryapplication.domain.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Singleton
    @Binds
    abstract fun bindWordRepo(wordRepositoryImpl: WordRepositoryImpl): WordRepository

}