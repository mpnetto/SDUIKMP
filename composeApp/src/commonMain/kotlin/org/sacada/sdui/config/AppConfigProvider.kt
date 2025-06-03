package org.sacada.sdui.config

expect object AppConfigProvider {
    fun getFigmaApiKey(): String

    fun getFigmaFileKey(): String
}
