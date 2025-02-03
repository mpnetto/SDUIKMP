package org.sacada.sdui.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.sacada.data.ui.screen.RenderScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MainScreen() {
    KoinContext {
        val viewModel = koinViewModel<MainScreenViewModel>()
        val updateKey = viewModel.updateKey.value
        val currentScreenIndex = viewModel.currentScreenIndex.value
        val rootComponent by viewModel.rootComponent
        val isLoading by viewModel.isLoading

        Box(
            modifier = Modifier
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
                }
        ) {
            AnimatedContent(
                targetState = currentScreenIndex,
                transitionSpec = {
                    if (targetState > initialState) {

                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        )
                    } else {

                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(300)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300)
                        )
                    }
                }
            ) { targetIndex ->
                key(updateKey) {
                    rootComponent?.screens?.getOrNull(targetIndex)?.let { RenderScreen(it) }
                }
            }


            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

    }


}

@Preview
@Composable
fun PreviewTestScreen() {
    MainScreen()
}
