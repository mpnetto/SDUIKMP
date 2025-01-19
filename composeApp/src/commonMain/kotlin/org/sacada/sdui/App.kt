package org.sacada.sdui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.core.module.Module
import org.sacada.sdui.ui.MainScreen

@Composable
@Preview
fun App(
    platformModule : Module = Module()
) {
//    KoinApplication(
//        application = {
//            modules(appModule, platformModule)
//      }
//    ) {
    KoinContext {
        MaterialTheme {
            MainScreen()
        }
    }

}