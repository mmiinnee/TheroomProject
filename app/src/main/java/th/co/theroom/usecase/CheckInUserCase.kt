package th.co.theroom.usecase

import th.co.theroom.bookingdata.BookingDataFragmentRepositoryImpl
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result

class CheckInUserCase(private val bookingDataFragmentRepositoryImpl: BookingDataFragmentRepositoryImpl) : BaseCoroutinesUseCase<BookingEntity, Boolean>() {
    override suspend fun execute(parameter: BookingEntity): Result<Boolean> {
        return try {
            val response = bookingDataFragmentRepositoryImpl.updateStatusBooking(parameter.rowId, "เข้าพัก")
            if (response != null) {
                Result.Success(true)
            } else {
                Result.Fail("เกิดข้อผิดพลาดในการบันทึก")
            }
        } catch (e: Exception) {
            return Result.Fail(e.toString())
        }
    }
}