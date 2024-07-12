package com.user.dictionaryapplication.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.use_case.PreviousWordListUseCase
import com.user.dictionaryapplication.domain.use_case.WordDefinitionUseCase
import com.user.dictionaryapplication.domain.util.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WordViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: WordViewModel
    private lateinit var dictionaryUseCase: WordDefinitionUseCase
    private lateinit var previousSearchListUseCase: PreviousWordListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dictionaryUseCase = mockk()
        previousSearchListUseCase = mockk()
        viewModel = WordViewModel(dictionaryUseCase, previousSearchListUseCase)
    }

    @Test
    fun `search with correct word should return the result with success`() {
        val word = "hello"
        val wordDefinition = "Greeting"
        val wordObj = Word(word, wordDefinition)
        val successResult = Result.Success(wordObj)


        coEvery { dictionaryUseCase.invoke(word) } returns successResult

        val observer = mockk<Observer<Result<Word>?>>(relaxed = true)

        viewModel.searchWordMeanings(word)

        viewModel.wordDefinition.observeForever(observer)

        verify {
            observer.onChanged(successResult)
        }

        viewModel.wordDefinition.removeObserver(observer)
    }


    @Test
    fun `search for the word that has not meaning in the dictionary should return error result`() {
        val word = "7465"
        val errorMessage = "No Definition Found for this Word"
        val errorResult = Result.Error(null, errorMessage)

        coEvery { dictionaryUseCase.invoke(word) } returns errorResult

        viewModel.searchWordMeanings(word)
        val observer = mockk<Observer<Result<Word>?>>(relaxed = true)

        viewModel.wordDefinition.observeForever(observer)

        verify {
            observer.onChanged(errorResult)
        }
        viewModel.wordDefinition.removeObserver(observer)
    }

    @Test
    fun `get the list of the previous search from local storage should return result with success`() {
        val word = "hello"
        val wordDefinition = "Greeting"
        val wordObj = Word(word, wordDefinition)
        val successResult = Result.Success(mutableListOf(wordObj, wordObj))


        coEvery { previousSearchListUseCase.invoke() } returns successResult

        val observer = mockk<Observer<in Result<List<Word>?>>>(relaxed = true)

        viewModel.loadPreviousWordList()

        viewModel.previousWordList.observeForever(observer)

        verify {
            observer.onChanged(successResult)
        }

        viewModel.previousWordList.removeObserver(observer)
    }

    @Test
    fun `test get the list of the previous search from local storage should return result with Error`() {

        val errorResult = Result.Error(data = null, "Generic error has been occurred")


        coEvery { previousSearchListUseCase.invoke() } returns errorResult

        val observer = mockk<Observer<in Result<List<Word>?>>>(relaxed = true)

        viewModel.loadPreviousWordList()

        viewModel.previousWordList.observeForever(observer)

        verify {
            observer.onChanged(errorResult)
        }

        viewModel.previousWordList.removeObserver(observer)
    }


    @Test
    fun `load the previous search list`() {
        val loadingResult = Result.Loading(null)

        coEvery { previousSearchListUseCase.invoke() } returns loadingResult

        viewModel.loadPreviousWordList()
        val observer = mockk<Observer<in Result<List<Word>?>>>(relaxed = true)

        viewModel.previousWordList.observeForever(observer)

        verify {
            observer.onChanged(loadingResult)
        }
        viewModel.previousWordList.removeObserver(observer)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }
}