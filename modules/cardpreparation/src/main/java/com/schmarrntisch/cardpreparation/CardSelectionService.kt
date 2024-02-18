package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.model.UnfilledPicoloCard

internal interface CardSelectionService {
    fun selectCardsForGame(
        cardsByCategory: Map<Category, List<UnfilledPicoloCard>>,
        numberOfPlayers: Int
    ): List<UnfilledPicoloCard>
}