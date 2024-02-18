package com.schmarrntisch.cardpreparation.di

import com.schmarrntisch.cardpreparation.CardExpansionService
import com.schmarrntisch.cardpreparation.CardFillService
import com.schmarrntisch.cardpreparation.CardInflationService
import com.schmarrntisch.cardpreparation.CardSelectionService
import com.schmarrntisch.cardpreparation.implementation.CardFillServiceImpl
import com.schmarrntisch.cardpreparation.implementation.CardInflationServiceImpl
import com.schmarrntisch.cardpreparation.implementation.CardSelectionServiceImpl
import com.schmarrntisch.cardpreparation.implementation.CardsExpansionServiceImpl
import org.koin.dsl.module

internal val cardPreparationModuleServices = module {
    single<CardExpansionService> { CardsExpansionServiceImpl(cardsFileRepository = get()) }
    single<CardSelectionService> { CardSelectionServiceImpl() }
    single<CardFillService> { CardFillServiceImpl() }
    single<CardInflationService> { CardInflationServiceImpl() }
}