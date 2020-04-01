package th.co.theroom.usecase

import th.co.theroom.bookingdata.BookingDataFragmentRepositoryImpl
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result

class SelectBookingListUseCase(private val bookingDataFragmentRepositoryImpl: BookingDataFragmentRepositoryImpl) : BaseCoroutinesUseCase<Unit, MutableList<BookingEntity>>() {
    override suspend fun execute(parameter: Unit): Result<MutableList<BookingEntity>> {
        return try {
            val response = bookingDataFragmentRepositoryImpl.selectBookingList()
            if (response != null) {
                Result.Success(response)
            } else {
                Result.Fail("พบปัญหาในการโหลดข้อมูล")
            }
        } catch (e: Exception) {
            Result.Fail(e.toString())
        }
    }
}