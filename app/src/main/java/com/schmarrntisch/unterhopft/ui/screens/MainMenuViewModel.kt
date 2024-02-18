package com.schmarrntisch.unterhopft.ui.screens

import com.schmarrntisch.appbase.model.PicoloCard

class MainMenuViewModel(
    private val cardStackProvider: com.schmarrntisch.cardpreparation.CardStackProvider
) {
    fun getCardStack() : List<PicoloCard> = cardStackProvider.provideCardStack()
}