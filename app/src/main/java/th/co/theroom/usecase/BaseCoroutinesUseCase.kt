package th.co.theroom.usecase

import th.co.theroom.model.Result

abstract class BaseCoroutinesUseCase<in P, R> {
    abstract suspend fun execute(parameter: P): Result<R>
}