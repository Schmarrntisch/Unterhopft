package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.CardStackProvider

class CardStackProviderFakeImpl : CardStackProvider {
    override fun provideCardStack(playerNames: List<String>): List<PicoloCard> {
        return listOf(
            PicoloCard(text = "Text of card 1"),
            PicoloCard(text = "Text of card 2"),
            PicoloCard(text = "Text of card 3"),
            PicoloCard(text = "Text of card 4"),
            PicoloCard(text = "Text of card 5"),
            PicoloCard(text = "Text of card 6")
        )
    }
}