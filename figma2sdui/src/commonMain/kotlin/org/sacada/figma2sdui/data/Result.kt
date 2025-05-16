package org.sacada.figma2sdui.data

import kotlinx.serialization.Serializable

@Serializable
data class Result<T>(
    val hasError: Boolean,
    val data: T? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun <T> success(data: T): Result<T> = Result(false, data)

        fun <T> failure(errorMessage: String): Result<T> = Result(true, errorMessage = errorMessage)
    }
}
