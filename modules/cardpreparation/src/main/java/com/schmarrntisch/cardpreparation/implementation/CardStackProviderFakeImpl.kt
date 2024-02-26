package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.UnterhopftCard
import com.schmarrntisch.cardpreparation.CardStackProvider

class CardStackProviderFakeImpl : CardStackProvider {
    override fun provideCardStack(playerNames: List<String>): List<UnterhopftCard> {
        return listOf(
            UnterhopftCard(text = "Text of card 1", id = 1),
            UnterhopftCard(text = "Text of card 2", id = 2),
            UnterhopftCard(text = "Text of card 3", id = 2),
            UnterhopftCard(text = "Text of card 4", id = 3),
            UnterhopftCard(text = "Text of card 5", id = 4),
            UnterhopftCard(text = "Text of card 6", id = 5)
        )
    }
}