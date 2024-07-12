package com.user.dictionaryapplication.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DictionaryAppDatabaseTest {
    private lateinit var dictionaryDatabase: DictionaryAppDatabase
    private lateinit var dictionaryDao: DictionaryAppDao

    @Before
    fun setUp() {
        dictionaryDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DictionaryAppDatabase::class.java
        ).allowMainThreadQueries().build()

        dictionaryDao = dictionaryDatabase.dictionaryAppDao
    }

    @Test
    fun insertWordInDictionaryDataBase() = runBlocking {
        val wordEntity = WordEntity(
            1, "Hello", "Greetings"
        )

        dictionaryDao.insertWord(wordEntity)


        val cachedWord = dictionaryDao.getWord("Hello")


        assertEquals(cachedWord?.word, wordEntity.word)
    }


    @Test
    fun testSelectAllPreviousSearchListFromDictionaryDataBase() = runBlocking {
        val helloEntity = WordEntity(
            1, "Hello", "Greetings"
        )
        val happyEntity = WordEntity(
            2, "Happy", "Feeling Well"
        )
        val catEntity = WordEntity(
            3, "Cat", "Lovely Animal"
        )
        val mouseEntity = WordEntity(
            4, "Mouse", "Amall Animal"
        )

        dictionaryDao.insertWord(helloEntity)
        dictionaryDao.insertWord(happyEntity)
        dictionaryDao.insertWord(catEntity)
        dictionaryDao.insertWord(mouseEntity)


        val cachedWordList = dictionaryDao.getAllWordList()


        assertEquals(cachedWordList?.size, 4)
        assertTrue(cachedWordList!!.contains(helloEntity))
        assertTrue(cachedWordList!!.contains(happyEntity))
        assertTrue(cachedWordList!!.contains(catEntity))
        assertTrue(cachedWordList!!.contains(mouseEntity))
    }

    @After
    fun tearDown() {
        dictionaryDatabase.close()
    }
}