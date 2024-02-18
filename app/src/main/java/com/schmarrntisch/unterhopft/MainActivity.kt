package com.schmarrntisch.unterhopft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.schmarrntisch.appbase.di.appBaseModule
import com.schmarrntisch.cardpreparation.di.cardPreparationModule
import com.schmarrntisch.unterhopft.di.appModule
import com.schmarrntisch.unterhopft.ui.screens.NavGraphs
import com.schmarrntisch.unterhopft.ui.theme.UnterhopftTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule, cardPreparationModule, appBaseModule)
        }

        setContent {
            UnterhopftTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}