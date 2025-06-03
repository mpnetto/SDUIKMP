package org.sacada.sdui.config

import org.sacada.sdui.BuildConfig

actual object AppConfigProvider {
    actual fun getFigmaApiKey(): String = BuildConfig.FIGMA_API_KEY

    actual fun getFigmaFileKey(): String = BuildConfig.FIGMA_FILE_KEY
}
