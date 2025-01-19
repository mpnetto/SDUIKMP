package com.sacada.figma2sdui.data

interface Visitable {
    fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData? = null): T
}
