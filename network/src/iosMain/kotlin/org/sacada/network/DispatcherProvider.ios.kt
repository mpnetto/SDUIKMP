package org.sacada.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

class DispatcherProviderImpl : DispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO

    override val main: CoroutineDispatcher = MainDispatcher
}

object MainDispatcher : MainCoroutineDispatcher() {
    private val mainQueue: dispatch_queue_t = dispatch_get_main_queue()

    override val immediate: MainCoroutineDispatcher
        get() = this

    override fun dispatch(
        context: CoroutineContext,
        block: Runnable,
    ) {
        dispatch_async(mainQueue, block::run)
    }
}

actual fun getDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
