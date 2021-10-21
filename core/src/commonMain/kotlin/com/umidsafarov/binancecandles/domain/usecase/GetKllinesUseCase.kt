package com.umidsafarov.binancecandles.domain.usecase

import com.umidsafarov.binancecandles.data.BinanceRepository
import com.umidsafarov.binancecandles.domain.entity.Kline
import com.umidsafarov.binancecandles.domain.model.Either
import com.umidsafarov.binancecandles.domain.settings.ApplicationSettings

class GetKllinesUseCase(
    private val binanceRepository: BinanceRepository,
    private val settings: ApplicationSettings,
) :
    UseCase<List<Kline>, GetKllinesUseCase.Params>() {
    class Params(val forceReload: Boolean, val binanceSymbol: String, val binanceInterval: String)

    override suspend fun run(params: Params): Either<Exception, List<Kline>> {
        return try {
            val klines = binanceRepository.getKlines(
                params.forceReload,
                params.binanceSymbol,
                params.binanceInterval,
                settings.lastSync
            )
            if (klines.isNotEmpty())
                settings.saveSync(klines[0].timeClose)
            Either.Success(klines)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}