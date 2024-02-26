package com.schmarrntisch.unterhopft.ui.screens.unterhopft

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.schmarrntisch.unterhopft.ui.screens.destinations.UnterhopftCardScreenDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SelectPlayersScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier
) {
    val viewModel: UnterhopftViewModel = koinViewModel()
    SelectPlayersScreen(
        selectPlayersUiState = viewModel.selectPlayersUiStateFlow.collectAsStateWithLifecycle(),
        startGame = { navigator.navigate(UnterhopftCardScreenDestination) }
    )
}

@Composable
private fun SelectPlayersScreen(
    selectPlayersUiState: State<SelectPlayersUiState>,
    modifier: Modifier = Modifier,
    startGame: (List<String>) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select players screen")
        Button(onClick = { startGame(listOf("Player1", "Player2")) }) {
            Text("Start game")
        }
    }
}