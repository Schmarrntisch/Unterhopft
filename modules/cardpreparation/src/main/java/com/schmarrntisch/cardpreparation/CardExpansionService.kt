package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.model.UnfilledPicoloCard

internal interface CardExpansionService {
    fun provideCards(): Map<Category, List<UnfilledPicoloCard>>
}