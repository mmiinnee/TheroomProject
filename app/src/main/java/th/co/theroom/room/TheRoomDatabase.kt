package th.co.theroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.RoomEntity

@Database(entities = [RoomEntity::class, BookingEntity::class], version = 2, exportSchema = false)
abstract class TheRoomDatabase : RoomDatabase() {

    abstract fun theRoomDao(): TheRoomDao

    companion object {
        const val DB_NAME = "TheRoom.db"
    }
}