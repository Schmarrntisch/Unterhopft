package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.Category
import com.schmarrntisch.cardpreparation.model.UnfilledUnterhopftCard

internal interface CardExpansionService {
    fun provideCards(): Map<Category, List<UnfilledUnterhopftCard>>
}