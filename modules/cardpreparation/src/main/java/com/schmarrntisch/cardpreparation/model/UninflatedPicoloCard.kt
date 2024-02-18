package com.schmarrntisch.cardpreparation.model

import com.schmarrntisch.appbase.model.Category

internal data class UninflatedPicoloCard(
    val id: Int,
    val numberPlayers: Int,
    val category: Category,
    val texts: List<String>
)