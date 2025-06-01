package org.sacada.sdui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.sacada.sdui.ui.MainScreen

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            MainScreen()
        }
    }
}
