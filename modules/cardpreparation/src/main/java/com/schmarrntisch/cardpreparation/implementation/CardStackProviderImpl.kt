package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.CardStackProvider

internal class CardStackProviderImpl(
    private val cardsFileRepository: com.schmarrntisch.appbase.CardsFileRepository
) : CardStackProvider {
    override fun provideCardStack(): List<PicoloCard> {
        TODO("Not yet implemented")
    }
}