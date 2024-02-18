package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.PicoloCard
import com.schmarrntisch.cardpreparation.model.UninflatedPicoloCard

internal interface CardInflationService {
    fun inflateCards(uninflatedCardStack: List<UninflatedPicoloCard>): List<PicoloCard>
}