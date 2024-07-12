package com.user.dictionaryapplication.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class],
    version = 1
)
abstract class DictionaryAppDatabase : RoomDatabase() {
    abstract val dictionaryAppDao: DictionaryAppDao
}