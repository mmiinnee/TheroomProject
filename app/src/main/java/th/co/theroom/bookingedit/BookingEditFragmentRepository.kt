package th.co.theroom.bookingedit

import th.co.theroom.model.RoomEntity

interface BookingEditFragmentRepository {
    suspend fun selectRoomByBedTypeAll(buildingNumber: String, bedType: String): MutableList<RoomEntity>

    suspend fun selectRoomByPeopleSizeAll(buildingNumber: String, peopleSize: String): MutableList<RoomEntity>

    suspend fun editBookingRoom(rowId: Int, bookingName: String, buildingNumber: String, peopleSize: String? = null, bedType: String? = null, roomNumber: String, dateCheckIn: String, dateCheckOut: String, dateEditData: String)
}