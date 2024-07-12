package com.user.dictionaryapplication.data.di

import com.user.dictionaryapplication.data.error_handling.ErrorHandlerStrategy
import com.user.dictionaryapplication.data.error_handling.ErrorHandlerStrategyImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorHandlerModule {

    @Singleton
    @Binds
    abstract fun bindErrorHandler(errorHandlerStrategy: ErrorHandlerStrategyImpl): ErrorHandlerStrategy

}