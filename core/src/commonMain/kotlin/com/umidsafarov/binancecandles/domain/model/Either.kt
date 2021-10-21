package com.umidsafarov.binancecandles.domain.model

sealed class Either<out F, out S> {

    data class Failure<out F>(val failure: F) : Either<F, Nothing>()
    data class Success<out S>(val success: S) : Either<Nothing, S>()

    inline fun <T> fold(failed: (F) -> T, succeeded: (S) -> T): T =
        when (this) {
            is Failure -> failed(failure)
            is Success -> succeeded(success)
        }

    inline fun <F, S1, S2> Either<F, S1>.flatMap(succeeded: (S1) -> Either<F, S2>): Either<F, S2> =
        fold({ this as Failure }, succeeded)

    inline fun <F, S1, S2> Either<F, S1>.map(f: (S1) -> S2): Either<F, S2> =
        flatMap { Success(f(it)) }
}