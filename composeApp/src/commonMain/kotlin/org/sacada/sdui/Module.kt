package org.sacada.sdui

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.sacada.sdui.ui.MainScreenViewModel

val appModule = module {
    viewModelOf(::MainScreenViewModel)

}