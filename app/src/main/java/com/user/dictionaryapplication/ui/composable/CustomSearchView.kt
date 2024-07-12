package com.user.dictionaryapplication.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.user.dictionaryapplication.R

@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(colorResource(id = R.color.teal_700))

    ) {
        TextField(
            value = search,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.teal_700),
                placeholderColor = colorResource(id = R.color.white),
                leadingIconColor = colorResource(id = R.color.white),
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.white)
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            placeholder = { Text(text = stringResource(id = R.string.search_place_holder)) },
        )

    }

}