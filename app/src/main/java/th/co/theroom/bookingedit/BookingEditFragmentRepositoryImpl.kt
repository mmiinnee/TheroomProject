package th.co.theroom.bookingedit

import th.co.theroom.model.RoomEntity
import th.co.theroom.room.TheRoomDao

class BookingEditFragmentRepositoryImpl(private val theRoomDao: TheRoomDao) : BookingEditFragmentRepository {
    override suspend fun selectRoomByBedTypeAll(buildingNumber: String, bedType: String): MutableList<RoomEntity> = theRoomDao.selectRoomByBedTypeAll(buildingNumber, bedType)

    override suspend fun selectRoomByPeopleSizeAll(buildingNumber: String, peopleSize: String): MutableList<RoomEntity> = theRoomDao.selectRoomByPeopleSizeAll(buildingNumber, peopleSize)

    override suspend fun editBookingRoom(rowId: Int, bookingName: String, buildingNumber: String, peopleSize: String?, bedType: String?, roomNumber: String, dateCheckIn: String, dateCheckOut: String, dateEditData: String) {
        theRoomDao.editBookingRoom(rowId, bookingName, buildingNumber, peopleSize, bedType, roomNumber, dateCheckIn, dateCheckOut, dateEditData)
    }
}