package org.sacada.data.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigator: ProvidableCompositionLocal<NavHostController> =
    compositionLocalOf {
        error("Navigator not provided. Ensure LocalNavigator is provided within the CompositionLocalProvider.")
    }
