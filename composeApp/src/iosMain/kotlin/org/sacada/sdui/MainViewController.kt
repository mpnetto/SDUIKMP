package org.sacada.sdui

import androidx.compose.ui.window.ComposeUIViewController
import initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
) {
    App()
}