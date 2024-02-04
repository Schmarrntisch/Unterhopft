package com.schmarrntisch.unterhopft.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    Text("Main Menu")
}