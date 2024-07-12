package com.user.dictionaryapplication.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.user.dictionaryapplication.R
import com.user.dictionaryapplication.domain.entities.Word

@Composable
fun SearchListItem(
    wordItem: Word
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {
        Surface(
            elevation = 2.dp,
            color = colorResource(id = R.color.teal_700),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(CircleShape)
        ) {

            Text(
                text = "${wordItem.word} :${wordItem.definition}",
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )

        }
    }
}