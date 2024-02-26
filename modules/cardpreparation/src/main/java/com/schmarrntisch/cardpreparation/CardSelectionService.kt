package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.model.UnfilledUnterhopftCard

internal interface CardSelectionService {
    fun selectCardsForGame(
        cardsByCategory: Map<Category, List<UnfilledUnterhopftCard>>,
        numberOfPlayers: Int
    ): List<UnfilledUnterhopftCard>
}