package org.sacada.sdui

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.sacada.data.domain.useCase.GetSduiScreensUseCase
import org.sacada.data.repository.SduiRepository
import org.sacada.sdui.data.SduiRepositoryImpl
import org.sacada.sdui.ui.MainScreenViewModel

val appModule =
    module {
        viewModelOf(::MainScreenViewModel)

        factory { GetSduiScreensUseCase(get()) }

        single<SduiRepository> { SduiRepositoryImpl() }
    }
