package th.co.theroom.usecase

import th.co.theroom.model.Result
import th.co.theroom.model.RoomModel
import th.co.theroom.splashscreen.SplashScreenRepositoryImpl

class InsertRoomUserCase(private val splashScreenRepositoryImpl: SplashScreenRepositoryImpl) : BaseCoroutinesUseCase<RoomModel, Boolean>() {
    override suspend fun execute(parameter: RoomModel): Result<Boolean> {
        return try {
            parameter.data.forEach {
                splashScreenRepositoryImpl.insertRoom(it)
            }
            Result.Success(true)
        } catch (e: Exception) {
            return Result.Fail(e.toString())
        }
    }
}