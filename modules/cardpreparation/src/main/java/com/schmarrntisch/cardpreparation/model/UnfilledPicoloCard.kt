package com.schmarrntisch.cardpreparation.model

import com.schmarrntisch.appbase.model.Category

internal data class UnfilledPicoloCard(
    val numberPlayers: Int,
    val category: Category,
    val texts: List<String>,
    val cardId: Int
)
