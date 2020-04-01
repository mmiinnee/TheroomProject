package th.co.theroom.splashscreen

import th.co.theroom.model.RoomEntity
import th.co.theroom.room.TheRoomDao

class SplashScreenRepositoryImpl(private val theRoomDao: TheRoomDao) : SplashScreenRepository {
    override suspend fun insertRoom(roomEntity: RoomEntity) {
        theRoomDao.insertRoomData(roomEntity)
    }
}