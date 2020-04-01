package th.co.theroom.usecase

import android.util.Log
import th.co.theroom.bookingedit.BookingEditFragmentRepositoryImpl
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result

class EditBookingRoomUserCase(private val bookingEditFragmentRepositoryImpl: BookingEditFragmentRepositoryImpl) : BaseCoroutinesUseCase<BookingEntity, Unit>() {
    override suspend fun execute(parameter: BookingEntity): Result<Unit> {
        return try {
            Log.e("toei", parameter.toString())
            val response = bookingEditFragmentRepositoryImpl.editBookingRoom(parameter.rowId, parameter.bookingName, parameter.buildingNumber, parameter.peopleSize, parameter.bedType, parameter.roomNumber, parameter.dateCheckIn, parameter.dateCheckOut, parameter.dateEditData)
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