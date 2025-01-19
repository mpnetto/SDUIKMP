package com.sacada.data.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.sacada.core.model.ViewScreen
import com.sacada.data.ui.components.RenderComponent
import com.sacada.data.ui.components.bottomBar.BottomBarRenderer
import com.sacada.data.ui.components.topBar.TopBarRenderer

//val LocalScreenViewModel =
//    compositionLocalOf<ScreenViewModel> {
//        error("ScreenViewModel not provided")
//    }

@Composable
fun RenderScreen(screen: ViewScreen) {
//    CompositionLocalProvider(LocalScreenViewModel provides viewModel) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                screen.topBar?.let { TopBarRenderer.Render(it) }
            },
            bottomBar = {
                screen.bottomBar?.let { BottomBarRenderer.Render(it) }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                screen.layout?.let { RenderComponent(it) }
            }
        }
//    }
}
