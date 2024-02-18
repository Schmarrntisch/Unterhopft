package com.schmarrntisch.unterhopft.ui.screens.mainmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel: MainMenuViewModel = koinViewModel()

        var content by remember { mutableStateOf("") }
        Button(onClick = { content = viewModel.getCardStack().toString() }) {
            Text("Get card stack")
        }

        TextField(value = content, onValueChange = {}, modifier = Modifier.fillMaxSize())
    }

}