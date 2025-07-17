package org.sacada.network

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

expect fun getDispatcherProvider(): DispatcherProvider
