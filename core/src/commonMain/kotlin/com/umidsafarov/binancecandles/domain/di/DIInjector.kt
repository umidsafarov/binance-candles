package com.umidsafarov.binancecandles.domain.di

import com.umidsafarov.binancecandles.core.application.ApplicationSQLDelight
import com.umidsafarov.binancecandles.core.repository.RepositorySQLDelight
import com.umidsafarov.binancecandles.data.BinanceRepository
import com.umidsafarov.binancecandles.data.local.DatabaseDriverFactory
import com.umidsafarov.binancecandles.domain.settings.ApplicationSettings
import com.umidsafarov.binancecandles.domain.usecase.AuthorizeUseCase
import com.umidsafarov.binancecandles.domain.usecase.GetKllinesUseCase
import com.umidsafarov.binancecandles.presentation.presenter.AuthorizationPresenter
import com.umidsafarov.binancecandles.presentation.presenter.KlinesListPresenter
import io.ktor.utils.io.concurrent.*
import org.kodein.di.*

object DIInjector {
    object BINANCE_ENDPOINT_TAG
    object BINANCE_SYMBOL_TAG
    object BINANCE_INTERVAL_TAG
    object ENTITY_DATABASE_NAME_TAG
    object APPLICATION_DATABASE_NAME_TAG

    val di = DI.lazy {
        bindConstant(BINANCE_ENDPOINT_TAG) { "https://api.binance.com" }
        bindConstant(BINANCE_SYMBOL_TAG) { "BTCUSDT" }
        bindConstant(BINANCE_INTERVAL_TAG) { "1m" }
        bindConstant(ENTITY_DATABASE_NAME_TAG) { "repository.db" }
        bindConstant(APPLICATION_DATABASE_NAME_TAG) { "application.db" }

        bind<BinanceRepository>() with eagerSingleton {
            BinanceRepository(
                DatabaseDriverFactory(
                    RepositorySQLDelight.Schema,
                    instance(tag = ENTITY_DATABASE_NAME_TAG)
                ),
                instance(tag = BINANCE_ENDPOINT_TAG)
            )
        }

        bind<ApplicationSettings>() with eagerSingleton {
            ApplicationSettings(
                DatabaseDriverFactory(
                    ApplicationSQLDelight.Schema,
                    instance(tag = APPLICATION_DATABASE_NAME_TAG)
                )
            )
        }

        bind<AuthorizeUseCase>() with provider { AuthorizeUseCase(instance(), instance()) }
        bind<GetKllinesUseCase>() with provider { GetKllinesUseCase(instance(), instance()) }

        bind<KlinesListPresenter>() with provider {
            KlinesListPresenter(
                instance(),
                instance(tag = BINANCE_SYMBOL_TAG),
                instance(tag = BINANCE_INTERVAL_TAG)
            )
        }
        bind<AuthorizationPresenter>() with provider {
            AuthorizationPresenter(
                instance(),
                instance()
            )
        }
    }

    //giving access to swift code
    fun BinanceRepository() = di.direct.instance<BinanceRepository>()
    fun GetKlinesUseCase() = di.direct.instance<GetKllinesUseCase>()
    fun KlinesListPresenter() = di.direct.instance<KlinesListPresenter>()
}