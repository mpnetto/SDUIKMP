package org.sacada.data

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.sacada.data.ui.screen.ScreenViewModel

val dataModule =
    module {

        viewModelOf(::ScreenViewModel)
    }
