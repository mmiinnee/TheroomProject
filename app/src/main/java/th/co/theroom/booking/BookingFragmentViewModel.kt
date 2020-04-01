package th.co.theroom.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import th.co.theroom.base.BaseViewModel
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result
import th.co.theroom.model.RoomEntity
import th.co.theroom.usecase.InsertBookingRoomUserCase
import th.co.theroom.usecase.SelectRoomByBedTypeUseCase
import th.co.theroom.usecase.SelectRoomByPeopleSizeUseCase
import th.co.theroom.usecase.UpdateStatusRoomUserCase

class BookingFragmentViewModel(
        private val selectRoomByBedTypeUseCase: SelectRoomByBedTypeUseCase,
        private val selectRoomByPeopleSizeUseCase: SelectRoomByPeopleSizeUseCase,
        private val insertBookingRoomUserCase: InsertBookingRoomUserCase,
        private val updateStatusRoomUserCase: UpdateStatusRoomUserCase) : BaseViewModel() {

    val getRoomNumberLiveData: MutableLiveData<MutableList<RoomEntity>> = MutableLiveData()
    val bookingRoomLiveData: MutableLiveData<Unit> = MutableLiveData()

    fun getRoomNumberByBedType(buildingNumber: String, bedType: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = selectRoomByBedTypeUseCase.execute(Pair(buildingNumber, bedType))) {
                    is Result.Success -> {
                        getRoomNumberLiveData.postValue(result.data)
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }

    fun getRoomNumberByPeopleSize(buildingNumber: String, peopleSize: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = selectRoomByPeopleSizeUseCase.execute(Pair(buildingNumber, peopleSize))) {
                    is Result.Success -> {
                        getRoomNumberLiveData.postValue(result.data)
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }

    fun bookingRoom(bookingEntity: BookingEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = insertBookingRoomUserCase.execute(bookingEntity)) {
                    is Result.Success -> {
                        when (val resultStatusRoom = updateStatusRoomUserCase.execute(Pair(bookingEntity, false))) {
                            is Result.Success -> {
                                bookingRoomLiveData.postValue(Unit)
                            }
                            is Result.Fail -> {
                                errorLiveData.postValue(resultStatusRoom.exception)
                            }
                        }
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }
}