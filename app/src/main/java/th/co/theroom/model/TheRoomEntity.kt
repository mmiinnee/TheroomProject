package th.co.theroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RoomModel(
    @SerializedName("data")
    val data: List<RoomEntity> = listOf()
)

@Entity(tableName = "Room")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("rowId")
    var rowid: Int = 0,

    @ColumnInfo(name = "buildingNumber")
    @SerializedName("buildingNumber")
    var buildingNumber: String = "",

    @ColumnInfo(name = "roomNumber")
    @SerializedName("roomNumber")
    var roomNumber: String = "",

    @ColumnInfo(name = "peopleSize")
    @SerializedName("peopleSize")
    var peopleSize: String? = null,

    @ColumnInfo(name = "bedType")
    @SerializedName("bedType")
    var bedType: String? = null,

    @ColumnInfo(name = "status")
    @SerializedName("status")
    var status: Boolean = false
)

@Entity(tableName = "Booking")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("rowId")
    var rowId: Int = 0,

    @ColumnInfo(name = "bookingName")
    @SerializedName("bookingName")
    var bookingName: String = "",

    @ColumnInfo(name = "buildingNumber")
    @SerializedName("buildingNumber")
    var buildingNumber: String = "",

    @ColumnInfo(name = "peopleSize")
    @SerializedName("peopleSize")
    var peopleSize: String? = null,

    @ColumnInfo(name = "bedType")
    @SerializedName("bedType")
    var bedType: String? = null,

    @ColumnInfo(name = "roomNumber")
    @SerializedName("roomNumber")
    var roomNumber: String = "",

    @ColumnInfo(name = "dateCheckIn")
    @SerializedName("dateCheckIn")
    var dateCheckIn: String = "",

    @ColumnInfo(name = "dateCheckOut")
    @SerializedName("dateCheckOut")
    var dateCheckOut: String = "",

    @ColumnInfo(name = "status")
    @SerializedName("status")
    var status: String = "",

    @ColumnInfo(name = "dateEditData")
    @SerializedName("dateEditData")
    var dateEditData: String = ""
)