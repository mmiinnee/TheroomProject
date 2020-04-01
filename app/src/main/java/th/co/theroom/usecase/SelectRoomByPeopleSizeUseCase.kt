package th.co.theroom.usecase

import th.co.theroom.booking.BookingFragmentRepositoryImpl
import th.co.theroom.model.Result
import th.co.theroom.model.RoomEntity

class SelectRoomByPeopleSizeUseCase(private val bookingFragmentRepositoryImpl: BookingFragmentRepositoryImpl) : BaseCoroutinesUseCase<Pair<String, String>, MutableList<RoomEntity>>() {
    override suspend fun execute(parameter: Pair<String, String>): Result<MutableList<RoomEntity>> {
        return try {
            val (buildingNumber, peopleSize) = parameter
            val response = bookingFragmentRepositoryImpl.selectRoomByPeopleSize(buildingNumber, peopleSize, true)
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