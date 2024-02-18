package com.schmarrntisch.unterhopft.di

import com.schmarrntisch.unterhopft.ui.screens.MainMenuViewModel
import org.koin.dsl.module

val appModule = module {
    single { MainMenuViewModel(cardStackProvider = get()) } // TODO: Why does viewModel not work here
}