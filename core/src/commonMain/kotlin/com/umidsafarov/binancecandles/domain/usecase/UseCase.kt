package com.umidsafarov.binancecandles.domain.usecase

import com.umidsafarov.binancecandles.domain.model.Either
import com.umidsafarov.binancecandles.domain.uiDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Exception, Type>

    suspend operator fun invoke(
        params: Params,
        onSuccess: (Type) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val result = run(params)
        coroutineScope {
            launch(uiDispatcher) {
                result.fold(
                    failed = { onFailure(it) },
                    succeeded = { onSuccess(it) }
                )
            }
        }
    }

    //placeholder for empty parameters
    object None
}