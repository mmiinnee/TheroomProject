package th.co.theroom.usecase

import th.co.theroom.bookingedit.BookingEditFragmentRepositoryImpl
import th.co.theroom.model.Result
import th.co.theroom.model.RoomEntity

class SelectRoomByBedTypeAllUseCase(private val bookingEditFragmentRepositoryImpl: BookingEditFragmentRepositoryImpl) : BaseCoroutinesUseCase<Pair<String, String>, MutableList<RoomEntity>>() {
    override suspend fun execute(parameter: Pair<String, String>): Result<MutableList<RoomEntity>> {
        return try {
            val (buildingNumber, bedType) = parameter
            val response = bookingEditFragmentRepositoryImpl.selectRoomByBedTypeAll(buildingNumber, bedType)
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