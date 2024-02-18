package com.schmarrntisch.cardpreparation.di

import com.schmarrntisch.cardpreparation.CardStackProvider
import com.schmarrntisch.cardpreparation.implementation.CardStackProviderImpl
import org.koin.dsl.module

val cardPreparationModule = module {
    includes(cardPreparationModuleServices)

    single<CardStackProvider> {
        CardStackProviderImpl(
            cardExpansionService = get(),
            cardSelectionService = get(),
            cardFillService = get(),
            cardInflationService = get()
        )
    }
}