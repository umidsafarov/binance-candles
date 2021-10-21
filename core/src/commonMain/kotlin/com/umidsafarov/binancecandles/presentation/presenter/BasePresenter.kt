package com.umidsafarov.binancecandles.presentation.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V, N>(private val coroutineContext:CoroutineContext) {

    protected var view:V? = null
    protected var navigator:N? = null
    protected lateinit var scope: PresenterCoroutineScope

    protected open fun onViewAttached(view:V) {}
    protected open fun onViewDetached() {}

    fun attachView(view:V) {
        this.view = view
        scope = PresenterCoroutineScope(coroutineContext)
        onViewAttached(view)
    }

    fun detachView() {
        view = null
        scope.viewDetached()
        onViewDetached()
    }

    fun attachNavigator(navigator:N) {
        this.navigator = navigator
    }

    fun detachNavigator() {
        navigator = null;
    }
}

class PresenterCoroutineScope(context: CoroutineContext):CoroutineScope {
    private var onViewDetachJob = Job()
    override val coroutineContext: CoroutineContext = context + onViewDetachJob

    fun viewDetached() {
        onViewDetachJob.cancel()
    }
}