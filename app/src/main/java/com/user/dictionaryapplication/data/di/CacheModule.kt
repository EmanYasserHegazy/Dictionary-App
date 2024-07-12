package com.user.dictionaryapplication.data.di

import android.app.Application
import androidx.room.Room
import com.user.dictionaryapplication.data.cache.DictionaryAppDao
import com.user.dictionaryapplication.data.cache.DictionaryAppDatabase
import com.user.dictionaryapplication.data.source.local.WordLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(app: Application): DictionaryAppDatabase {
        return Room.databaseBuilder(
            app, DictionaryAppDatabase::class.java, "dictionary_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDao(dictionaryDB: DictionaryAppDatabase): DictionaryAppDao {
        return dictionaryDB.dictionaryAppDao
    }

    @Provides
    fun provideLocalDataSource(dictionaryAppDao: DictionaryAppDao): WordLocalDataSource {
        return WordLocalDataSource(dictionaryAppDao)
    }
}