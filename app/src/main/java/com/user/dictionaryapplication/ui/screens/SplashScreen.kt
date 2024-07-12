package com.user.dictionaryapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.user.dictionaryapplication.R
import com.user.dictionaryapplication.ui.navigation.Screen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    val purpleColor = colorResource(id = R.color.teal_700)
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(Screen.DictionaryScreen.name)
    }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            stringResource(id = R.string.splash),
            color = colorResource(R.color.teal_700),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 36.sp
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.splash_icon_contnet_description),
            modifier = Modifier
                .width(60.dp)
                .height(60.dp),
            tint = purpleColor,
        )
    }
}