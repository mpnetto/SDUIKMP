package org.sacada.sdui.data

import org.sacada.core.model.ViewScreens
import org.sacada.core.util.JsonParser
import org.sacada.data.repository.SduiRepository
import org.sacada.jsonbuilder.convertFigmaData
import org.sacada.sdui.config.AppConfigProvider

class SduiRepositoryImpl : SduiRepository {
    override suspend fun getScreens(): ViewScreens? {
        val apiKey = AppConfigProvider.getFigmaApiKey()
        val fileKey = AppConfigProvider.getFigmaFileKey()

        val resultJson =
            convertFigmaData(
                apiKey = apiKey,
                fileKey = fileKey,
            )

        return resultJson?.let {
            JsonParser.parseScreens(it.toString())
        }
    }
}
