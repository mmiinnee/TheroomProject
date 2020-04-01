package th.co.theroom.usecase

import th.co.theroom.bookingdata.BookingDataFragmentRepositoryImpl
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result

class CancelBookingUserCase(private val bookingDataFragmentRepositoryImpl: BookingDataFragmentRepositoryImpl) : BaseCoroutinesUseCase<BookingEntity, Boolean>() {
    override suspend fun execute(parameter: BookingEntity): Result<Boolean> {
        return try {
            val response = bookingDataFragmentRepositoryImpl.updateStatusBooking(parameter.rowId, "ยกเลิก")
            if (response != null) {
                Result.Success(true)
            } else {
                Result.Fail("เกิดข้อผิดพลาดในการยกเลิกห้องพัก")
            }
        } catch (e: Exception) {
            return Result.Fail(e.toString())
        }
    }
}