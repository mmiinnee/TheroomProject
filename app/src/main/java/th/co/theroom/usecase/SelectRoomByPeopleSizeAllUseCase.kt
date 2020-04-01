package th.co.theroom.usecase

import th.co.theroom.bookingedit.BookingEditFragmentRepositoryImpl
import th.co.theroom.model.Result
import th.co.theroom.model.RoomEntity

class SelectRoomByPeopleSizeAllUseCase(private val bookingEditFragmentRepositoryImpl: BookingEditFragmentRepositoryImpl) : BaseCoroutinesUseCase<Pair<String, String>, MutableList<RoomEntity>>() {
    override suspend fun execute(parameter: Pair<String, String>): Result<MutableList<RoomEntity>> {
        return try {
            val (buildingNumber, peopleSize) = parameter
            val response = bookingEditFragmentRepositoryImpl.selectRoomByPeopleSizeAll(buildingNumber, peopleSize)
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