package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.UnterhopftCard

interface CardStackProvider {
    fun provideCardStack(playerNames: List<String>): List<UnterhopftCard>
}