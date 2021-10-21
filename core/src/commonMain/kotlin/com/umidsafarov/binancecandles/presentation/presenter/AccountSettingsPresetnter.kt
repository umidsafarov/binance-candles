package com.umidsafarov.binancecandles.presentation.presenter

import com.umidsafarov.binancecandles.domain.defaultDispatcher
import com.umidsafarov.binancecandles.domain.settings.ApplicationSettings
import com.umidsafarov.binancecandles.domain.usecase.AuthorizeUseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthorizationPresenter(
    val authorizeUseCase: AuthorizeUseCase,
    val applicationSettings: ApplicationSettings,
    coroutineScope: CoroutineContext = defaultDispatcher
) : BasePresenter<AuthorizationView, AuthorizationNavigator>(coroutineScope) {

    override fun onViewAttached(view: AuthorizationView) {
        view.setCurrentUser(applicationSettings.userKey, applicationSettings.userSecret)
    }

    fun setUser(key: String, secret: String) {
        scope.launch {
            authorizeUseCase(
                AuthorizeUseCase.Params(key, secret),
                onFailure = { view?.showError(it.message.toString()) },
                onSuccess = {
                    back()
                })
        }
    }

    fun back() {
        navigator?.back()
    }
}

interface AuthorizationView {
    fun setCurrentUser(key: String, secret: String)
    fun showError(message: String)
}

interface AuthorizationNavigator {
    fun back()
}