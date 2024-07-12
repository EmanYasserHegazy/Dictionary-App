package com.user.dictionaryapplication.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.use_case.PreviousWordListUseCase
import com.user.dictionaryapplication.domain.use_case.WordDefinitionUseCase
import com.user.dictionaryapplication.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordDefinitionUseCase: WordDefinitionUseCase,
    private val previousWordListUseCase: PreviousWordListUseCase
) :
    ViewModel() {

    private val _wordDefinition = MutableLiveData<Result<Word>?>()
    val wordDefinition: LiveData<Result<Word>?> = _wordDefinition

    private val _previousWordList = MutableLiveData<Result<List<Word>?>>()
    val previousWordList: LiveData<Result<List<Word>?>> = _previousWordList

    init {
        loadPreviousWordList()
    }

    fun loadPreviousWordList() {
        viewModelScope.launch {
            _previousWordList.value = Result.Loading()
            try {
                _previousWordList.value = previousWordListUseCase.invoke()
            } catch (e: Exception) {
                _previousWordList.value = Result.Error(null, "General error occured")
            }
        }
    }


    fun searchWordMeanings(word: String?) {
        _wordDefinition.value = Result.Loading()
        if (word.isNullOrBlank()) {
            _wordDefinition.value = Result.Error(null, "Please enter a non-empty word")
            return
        }

        viewModelScope.launch {
            try {
                _wordDefinition.value = wordDefinitionUseCase.invoke(word)
            } catch (e: Exception) {
                _wordDefinition.value = Result.Error(null, e.message)
            }
        }
    }

}