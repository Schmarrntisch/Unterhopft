package com.schmarrntisch.unterhopft.ui.screens.selectplayers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectPlayersScreen(modifier: Modifier = Modifier) {
    val viewModel: SelectPlayersViewModel = koinViewModel()
}