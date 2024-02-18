package com.schmarrntisch.appbase.di

import com.schmarrntisch.appbase.CardsFileRepository
import com.schmarrntisch.appbase.CardsFileRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appBaseModule = module {
    single<CardsFileRepository> {
        CardsFileRepositoryImpl(context = androidContext()) }
}