package com.schmarrntisch.cardpreparation.implementation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.CardExpansionService
import com.schmarrntisch.cardpreparation.model.UnfilledPicoloCard

internal class CardsExpansionServiceImpl(
    private val cardsFileRepository: com.schmarrntisch.appbase.CardsFileRepository
) : CardExpansionService {
    override fun provideCards(): Map<Category, List<UnfilledPicoloCard>> {
        TODO("Not yet implemented")
    }
}