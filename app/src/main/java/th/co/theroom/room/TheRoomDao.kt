package th.co.theroom.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.RoomEntity

@Dao
interface TheRoomDao {

    @Insert
    suspend fun insertRoomData(roomEntity: RoomEntity)

    @Query("SELECT * FROM Room WHERE buildingNumber = :buildingNumber AND bedType = :bedType AND status = :status")
    suspend fun selectRoomByBedType(buildingNumber: String, bedType: String, status: Boolean): MutableList<RoomEntity>

    @Query("SELECT * FROM Room WHERE buildingNumber = :buildingNumber AND peopleSize = :peopleSize AND status = :status")
    suspend fun selectRoomByPeopleSize(buildingNumber: String, peopleSize: String, status: Boolean): MutableList<RoomEntity>

    @Insert
    suspend fun insertBookingRoomData(bookingEntity: BookingEntity)

    @Query("UPDATE Room SET status = :status WHERE buildingNumber = :buildingNumber AND roomNumber = :roomNumber")
    suspend fun updateStatusRoom(buildingNumber: String, roomNumber: String, status: Boolean)

    @Query("SELECT * FROM Booking")
    suspend fun selectBookingList(): MutableList<BookingEntity>

    @Query("UPDATE Booking SET status = :status WHERE rowId = :rowId")
    suspend fun updateStatusBooking(rowId: Int, status: String)

    @Query("SELECT * FROM Room WHERE buildingNumber = :buildingNumber AND bedType = :bedType")
    suspend fun selectRoomByBedTypeAll(buildingNumber: String, bedType: String): MutableList<RoomEntity>

    @Query("SELECT * FROM Room WHERE buildingNumber = :buildingNumber AND peopleSize = :peopleSize")
    suspend fun selectRoomByPeopleSizeAll(buildingNumber: String, peopleSize: String): MutableList<RoomEntity>

    @Query("UPDATE Booking SET bookingName = :bookingName, buildingNumber = :buildingNumber, peopleSize = :peopleSize, bedType = :bedType, roomNumber = :roomNumber, dateCheckIn = :dateCheckIn, dateCheckOut = :dateCheckOut, dateEditData = :dateEditData WHERE rowId = :rowId")
    suspend fun editBookingRoom(rowId: Int, bookingName: String, buildingNumber: String, peopleSize: String?, bedType: String?, roomNumber: String, dateCheckIn: String, dateCheckOut: String, dateEditData: String)

}