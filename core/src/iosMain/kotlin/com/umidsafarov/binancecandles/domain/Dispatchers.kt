package com.umidsafarov.binancecandles.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = IosMainDispatcher

actual val defaultDispatcher: CoroutineContext
    get() = IosMainDispatcher

private object IosMainDispatcher : CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) { block.run() }
    }
}

//Use main dispatcher for booth cases until KMM multithreading works well for iOS
//private object IosDefaultDispatcher : CoroutineDispatcher() {
//    override fun dispatch(context: CoroutineContext, block: Runnable) {
//        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0.toULong())) {
//            block.freeze().run()
//        }
//    }
//}