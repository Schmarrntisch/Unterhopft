package com.schmarrntisch.unterhopft.ui.screens.unterhopft

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UnterhopftViewModel : ViewModel() {
    private var selectPlayersUiState = MutableStateFlow(SelectPlayersUiState(listOf()))
    val selectPlayersUiStateFlow = selectPlayersUiState.asStateFlow()
}