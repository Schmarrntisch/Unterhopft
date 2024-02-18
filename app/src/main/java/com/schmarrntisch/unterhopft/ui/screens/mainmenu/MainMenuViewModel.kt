package com.schmarrntisch.unterhopft.ui.screens.mainmenu

import androidx.lifecycle.ViewModel
import com.schmarrntisch.appbase.model.PicoloCard

class MainMenuViewModel(
    private val cardStackProvider: com.schmarrntisch.cardpreparation.CardStackProvider
) : ViewModel() {
    fun getCardStack() : List<PicoloCard> = cardStackProvider.provideCardStack(listOf("Player1", "Player2", "Player3"))
}