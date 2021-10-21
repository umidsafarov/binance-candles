package com.umidsafarov.binancecandles.domain.usecase

import com.umidsafarov.binancecandles.data.BinanceRepository
import com.umidsafarov.binancecandles.domain.model.Either
import com.umidsafarov.binancecandles.domain.settings.ApplicationSettings

class AuthorizeUseCase(
    private val applicationSettings: ApplicationSettings,
    private val binanceRepository: BinanceRepository
) : UseCase<UseCase.None, AuthorizeUseCase.Params>() {

    data class Params(val key: String, val secret: String)

    override suspend fun run(params: Params): Either<Exception, None> {
        return try {
            applicationSettings.saveUser(params.key, params.secret)
            binanceRepository.authorize(params.key, params.secret)
            Either.Success(None)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}