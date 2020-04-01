package th.co.theroom.splashscreen

import th.co.theroom.model.RoomEntity

interface SplashScreenRepository {
    suspend fun insertRoom(roomEntity: RoomEntity)
}