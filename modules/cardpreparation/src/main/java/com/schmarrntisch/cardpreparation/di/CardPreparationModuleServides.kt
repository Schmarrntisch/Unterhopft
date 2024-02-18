package com.schmarrntisch.cardpreparation.di

import com.schmarrntisch.cardpreparation.CardStackProvider
import com.schmarrntisch.cardpreparation.implementation.CardStackProviderImpl
import org.koin.dsl.module

internal val cardPreparationModuleServices = module{
    single<CardStackProvider> {CardStackProviderImpl(cardsFileRepository = get())}
}