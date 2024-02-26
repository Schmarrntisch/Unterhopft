package com.schmarrntisch.cardpreparation

import com.schmarrntisch.cardpreparation.model.UnfilledUnterhopftCard
import com.schmarrntisch.cardpreparation.model.UninflatedUnterhopftCard

internal interface CardFillService {
    fun fillCards(
        unfilledCards: List<UnfilledUnterhopftCard>,
        players: List<String>
    ): List<UninflatedUnterhopftCard>
}