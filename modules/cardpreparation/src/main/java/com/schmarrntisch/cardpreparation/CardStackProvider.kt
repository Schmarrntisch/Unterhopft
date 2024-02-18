package com.schmarrntisch.cardpreparation

import com.schmarrntisch.appbase.model.PicoloCard

interface CardStackProvider {
    fun provideCardStack(): List<PicoloCard>
}