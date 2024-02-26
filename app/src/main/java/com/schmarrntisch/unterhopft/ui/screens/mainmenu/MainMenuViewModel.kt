package com.schmarrntisch.unterhopft.ui.screens.mainmenu

import androidx.lifecycle.ViewModel
import com.schmarrntisch.appbase.model.UnterhopftCard

class MainMenuViewModel(
    private val cardStackProvider: com.schmarrntisch.cardpreparation.CardStackProvider
) : ViewModel() {
    fun getCardStack() : List<UnterhopftCard> = cardStackProvider.provideCardStack(listOf("Player1", "Player2", "Player3"))
}