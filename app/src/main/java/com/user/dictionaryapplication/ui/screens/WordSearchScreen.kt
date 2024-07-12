package com.user.dictionaryapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.user.dictionaryapplication.R
import com.user.dictionaryapplication.domain.entities.Word
import com.user.dictionaryapplication.domain.util.Result
import com.user.dictionaryapplication.presentation.vm.WordViewModel
import com.user.dictionaryapplication.ui.composable.CustomRoundedButton
import com.user.dictionaryapplication.ui.composable.CustomSearchView
import com.user.dictionaryapplication.ui.composable.LoadingIndicator
import com.user.dictionaryapplication.ui.composable.PreviousSearchResultList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(viewModel: WordViewModel = hiltViewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val wordDefinition by viewModel.wordDefinition.observeAsState()
    val previousWordItemList by viewModel.previousWordList.observeAsState()

    var showWordDefinition by remember { mutableStateOf(false) }
    var clearData by remember { mutableStateOf(false) }
    var word by remember { mutableStateOf("") }

    LaunchedEffect(key1 = wordDefinition) {
        viewModel.loadPreviousWordList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchView(
            search = word,
            onValueChange = { word = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomRoundedButton(
                text = stringResource(id = R.string.submit_btn_text),
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(Color(R.color.teal_700)),
                onClick = {
                    keyboardController?.hide()
                    viewModel.searchWordMeanings(word)
                    clearData = true
                    showWordDefinition = true
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            CustomRoundedButton(
                text = stringResource(id = R.string.clear_btn_text),
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(color = Color.Red),
                onClick = {
                    word = ""
                    clearData = false
                    showWordDefinition = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showWordDefinition) {
            when (wordDefinition) {
                is Result.Success -> {
                    val data = (wordDefinition as Result.Success<Word>).data
                    if (data != null) {
                        Text(
                            "${data.definition}",
                            color = colorResource(R.color.teal_700),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                        )

                    }
                }

                is Result.Error -> {
                    Text(
                        "Error: ${(wordDefinition as Result.Error).message}",
                        color = colorResource(R.color.red),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                    )
                }

                is Result.Loading -> {
                    LoadingIndicator()
                }

                else -> {
                    // empty for now
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (previousWordItemList) {
            is Result.Success -> {
                (previousWordItemList as Result.Success<List<Word>?>).data?.let {
                    PreviousSearchResultList(it)
                }
            }

            is Result.Error -> {
                PreviousSearchResultList(emptyList())
            }

            is Result.Loading -> {
                LoadingIndicator()
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}