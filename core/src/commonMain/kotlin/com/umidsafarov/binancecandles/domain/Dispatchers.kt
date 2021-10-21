package com.umidsafarov.binancecandles.domain

import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext
expect val uiDispatcher: CoroutineContext
