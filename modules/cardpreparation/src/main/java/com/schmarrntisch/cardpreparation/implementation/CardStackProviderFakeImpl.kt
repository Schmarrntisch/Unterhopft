package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.CardStackProvider

class CardStackProviderFakeImpl : CardStackProvider {
    override fun provideCardStack(): List<PicoloCard> {
        return listOf(
            PicoloCard(text = listOf("Text of card 1")),
            PicoloCard(
                text = listOf(
                    "Text 1 of card 2",
                    "Text 2 of card 2"
                )
            ),
            PicoloCard(text = listOf("Text of card 3")),
            PicoloCard(text = listOf("Text of card 4")),
            PicoloCard(text = listOf("Text of card 5")),
            PicoloCard(text = listOf("Text of card 6")),
            PicoloCard(text = listOf("Text of card 7"))
        )
    }
}