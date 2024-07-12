package com.user.dictionaryapplication.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.dictionaryapplication.domain.entities.Word
import androidx.compose.foundation.lazy.items

@Composable
fun PreviousSearchResultList(
    wordList: List<Word>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(wordList) { word ->
            SearchListItem(word)
        }
    }
}


