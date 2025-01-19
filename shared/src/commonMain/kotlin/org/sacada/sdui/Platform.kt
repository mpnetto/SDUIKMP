package org.sacada.sdui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform