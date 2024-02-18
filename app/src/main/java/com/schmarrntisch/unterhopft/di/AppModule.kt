package com.schmarrntisch.unterhopft.di

import com.schmarrntisch.unterhopft.ui.screens.MainMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainMenuViewModel(cardStackProvider = get()) }
}