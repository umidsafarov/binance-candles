package com.umidsafarov.binancecandles.presentation.presenter

import com.umidsafarov.binancecandles.domain.defaultDispatcher
import com.umidsafarov.binancecandles.domain.entity.Kline
import com.umidsafarov.binancecandles.domain.usecase.GetKllinesUseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KlinesListPresenter(
    private val getKllinesUseCase: GetKllinesUseCase,
    private val binanceSymbol: String,
    private val binanceInterval: String,
    coroutineScope: CoroutineContext = defaultDispatcher
) : BasePresenter<KlinesListView, KlinesListNavigator>(coroutineScope) {

    override fun onViewAttached(view: KlinesListView) {
        view.showLoading()
        view.setTitle("${binanceSymbol}_${binanceInterval}")
        getKlines(false)
    }

    fun getKlines(forceReload: Boolean) {
        view?.showLoading()
        scope.launch {
            getKllinesUseCase(
                GetKllinesUseCase.Params(forceReload, binanceSymbol, binanceInterval),
                onFailure = { view?.showFetchingError(it.message.toString()) },
                onSuccess = {
                    view?.setKlines(it)
                    view?.showKlines()
                })
        }
    }

    fun authorize() {
        navigator?.openAuthorization()
    }
}

interface KlinesListView {
    fun showLoading()
    fun showKlines()
    fun showFetchingError(message: String)

    fun setTitle(title: String)
    fun setKlines(klines: List<Kline>)
}

interface KlinesListNavigator {
    fun openAuthorization()
}
