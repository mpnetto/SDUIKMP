package org.sacada.sdui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.sacada.core.model.ViewScreens
import org.sacada.data.navigation.LocalNavigator
import org.sacada.data.ui.screen.RenderScreen

const val LOADING_SCREEN_ROUTE = "loading"
const val SCREEN_ROUTE_PATTERN = "screen/{screenId}"
const val ERROR_SCREEN_ROUTE = "error"

@Composable
fun MainScreen() {
    KoinContext {
        val viewModel = koinViewModel<MainScreenViewModel>()

        val initialScreenId = viewModel.currentScreenId.value
        val rootComponent by viewModel.rootComponent
        val isLoading by viewModel.isLoading
        val errorMessage by viewModel.errorMessage

        MainScreenUI(
            rootComponent = rootComponent,
            initialScreenId = initialScreenId,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onNavigateToScreen = { index -> viewModel.goToScreen(index) },
            onRetry = { viewModel.fetchData(showLoading = true) },
        )
    }
}

@Composable
fun MainScreenUI(
    rootComponent: ViewScreens?,
    initialScreenId: String?,
    isLoading: Boolean,
    errorMessage: String?,
    onNavigateToScreen: (Int) -> Unit,
    onRetry: () -> Unit,
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(LocalNavigator provides navController) {
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
                                    onNavigateToScreen(index)

                                    scope.launch {
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
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination =
                        when {
                            errorMessage != null -> ERROR_SCREEN_ROUTE
                            initialScreenId != null -> "screen/$initialScreenId"
                            else -> LOADING_SCREEN_ROUTE
                        },
                ) {
                    composable(route = LOADING_SCREEN_ROUTE) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    composable(route = ERROR_SCREEN_ROUTE) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text =
                                    errorMessage
                                        ?: "An unknown error occurred.",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                            Button(onClick = onRetry) {
                                Text("Try Again")
                            }
                        }
                    }
                    composable(
                        route = SCREEN_ROUTE_PATTERN,
                        arguments =
                            listOf(
                                navArgument("screenId") {
                                    type = NavType.StringType
                                },
                            ),
                    ) { backStackEntry ->
                        val screenId =
                            backStackEntry.arguments?.getString("screenId")
                        if (screenId != null) {
                            val viewScreen =
                                rootComponent?.screens?.find { it.id == screenId }
                            if (viewScreen != null) {
                                RenderScreen(viewScreen)
                            }
                        }
                    }
                }
                if (isLoading && navController.currentDestination?.route != LOADING_SCREEN_ROUTE) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
