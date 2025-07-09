package org.sacada.sdui.config

import kotlinx.cinterop.toKString
import platform.posix.getenv

actual object AppConfigProvider {
    actual fun getFigmaApiKey(): String =
        getenv("FIGMA_API_KEY")?.toKString() ?: ""

    actual fun getFigmaFileKey(): String =
        getenv("FIGMA_FILE_KEY")?.toKString() ?: ""
}
