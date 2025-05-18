package org.sacada.sdui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.sacada.data.ui.screen.RenderScreen

@Composable
fun MainScreen() {
    KoinContext {
        val viewModel = koinViewModel<MainScreenViewModel>()
        val updateKey = viewModel.updateKey.value
        val currentScreenIndex = viewModel.currentScreenIndex.value
        val rootComponent by viewModel.rootComponent
        val isLoading by viewModel.isLoading

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount > 50) {
                                viewModel.goToPreviousScreen()
                            } else if (dragAmount < -50) {
                                viewModel.goToNextScreen()
                            }
//                        if (dragAmount > 50 || dragAmount < -50) {
//                            viewModel.fetchData(showLoading = true)
//                        }
                        }
                    },
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .verticalScroll(rememberScrollState()),
                        ) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Pages",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleLarge,
                            )
                            HorizontalDivider()

                            Text(
                                "Section 1",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium,
                            )

                            rootComponent?.screens?.forEachIndexed { index, screen ->
                                NavigationDrawerItem(
                                    label = { screen.name?.let { Text(it) } },
                                    selected = false,
                                    onClick = {
                                        viewModel.goToScreen(index)
                                        coroutineScope.launch {
                                            drawerState.close()
                                        }
                                    },
                                )
                            }

                            Spacer(Modifier.height(12.dp))
                        }
                    }
                },
            ) {
                key(updateKey) {
                    rootComponent?.screens?.getOrNull(currentScreenIndex)?.let { RenderScreen(it) }
                }
            }

            if (isLoading) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black
                                    .copy(alpha = 0.3f),
                            ),
                ) {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                    )
                }
            }
        }
    }
}
