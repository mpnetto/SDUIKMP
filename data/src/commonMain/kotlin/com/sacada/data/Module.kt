package com.sacada.data

import com.sacada.data.ui.screen.ScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {

    viewModelOf(::ScreenViewModel)

}