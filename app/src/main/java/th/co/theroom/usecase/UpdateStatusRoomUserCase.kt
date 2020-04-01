package th.co.theroom.usecase

import th.co.theroom.booking.BookingFragmentRepositoryImpl
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result
import th.co.theroom.model.RoomModel
import th.co.theroom.splashscreen.SplashScreenRepositoryImpl

class UpdateStatusRoomUserCase(private val bookingFragmentRepositoryImpl: BookingFragmentRepositoryImpl) : BaseCoroutinesUseCase<Pair<BookingEntity, Boolean>, Unit>() {
    override suspend fun execute(parameter: Pair<BookingEntity, Boolean>): Result<Unit> {
        return try {
            val (bookingEntity, status) = parameter
            val response = bookingFragmentRepositoryImpl.updateStatusRoom(bookingEntity.buildingNumber, bookingEntity.roomNumber, status)
            if (response != null) {
                Result.Success(Unit)
            } else {
                Result.Fail("เกิดข้อผิดพลาดในการบันทึกข้อมูล")
            }
        } catch (e: Exception) {
            return Result.Fail(e.toString())
        }
    }
}