package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.UnterhopftCard
import com.schmarrntisch.cardpreparation.model.UninflatedUnterhopftCard

internal interface CardInflationService {
    fun inflateCards(uninflatedCardStack: List<UninflatedUnterhopftCard>): List<UnterhopftCard>
}