package org.sacada.data.domain.useCase

import org.sacada.core.model.ViewScreens
import org.sacada.data.repository.SduiRepository

class GetSduiScreensUseCase(
    private val sduiRepository: SduiRepository,
) {
    suspend operator fun invoke(): ViewScreens? = sduiRepository.getScreens()
}
