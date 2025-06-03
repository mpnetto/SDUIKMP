package org.sacada.data.repository

import org.sacada.core.model.ViewScreens

interface SduiRepository {
    suspend fun getScreens(): ViewScreens?
}
