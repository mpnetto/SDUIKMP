package org.sacada.figma2sdui.data

interface Visitable {
    fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any? = null,
    ): T
}
