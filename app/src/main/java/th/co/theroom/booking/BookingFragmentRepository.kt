package th.co.theroom.booking

import th.co.theroom.model.BookingEntity
import th.co.theroom.model.RoomEntity

interface BookingFragmentRepository {
    suspend fun selectRoomByBedType(buildingNumber: String, bedType: String, status: Boolean): MutableList<RoomEntity>

    suspend fun selectRoomByPeopleSize(buildingNumber: String, peopleSize: String, status: Boolean): MutableList<RoomEntity>

    suspend fun insertBookingRoomData(bookingEntity: BookingEntity)

    suspend fun updateStatusRoom(buildingNumber: String, roomNumber: String, status: Boolean)
}