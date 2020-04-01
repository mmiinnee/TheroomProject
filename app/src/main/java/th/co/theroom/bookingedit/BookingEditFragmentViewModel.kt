package th.co.theroom.bookingedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import th.co.theroom.base.BaseViewModel
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result
import th.co.theroom.model.RoomEntity
import th.co.theroom.usecase.EditBookingRoomUserCase
import th.co.theroom.usecase.SelectRoomByBedTypeAllUseCase
import th.co.theroom.usecase.SelectRoomByPeopleSizeAllUseCase
import th.co.theroom.usecase.UpdateStatusRoomUserCase

class BookingEditFragmentViewModel(private val selectRoomByBedTypeAllUseCase: SelectRoomByBedTypeAllUseCase,
                                   private val selectRoomByPeopleSizeAllUseCase: SelectRoomByPeopleSizeAllUseCase,
                                   private val editBookingRoomUserCase: EditBookingRoomUserCase,
                                   private val updateStatusRoomUserCase: UpdateStatusRoomUserCase) : BaseViewModel() {

    val getRoomNumberLiveData: MutableLiveData<MutableList<RoomEntity>> = MutableLiveData()
    val sortedRoomNumberLiveData: MutableLiveData<MutableList<RoomEntity>> = MutableLiveData()
    val editBookingRoomLiveData: MutableLiveData<Unit> = MutableLiveData()

    fun getRoomNumberByBedTypeAll(buildingNumber: String, bedType: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result =
                        selectRoomByBedTypeAllUseCase.execute(Pair(buildingNumber, bedType))) {
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

    fun getRoomNumberByPeopleSizeAll(buildingNumber: String, peopleSize: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result =
                        selectRoomByPeopleSizeAllUseCase.execute(Pair(buildingNumber, peopleSize))) {
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

    fun editBookingRoom(bookingEntity: BookingEntity, changeRoom: Boolean, bookingEntityBeforeData: BookingEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = editBookingRoomUserCase.execute(bookingEntity)) {
                    is Result.Success -> {
                        if (changeRoom) {
                            when (val resultUpdateBeforeRoom = updateStatusRoomUserCase.execute(
                                    Pair(
                                            bookingEntityBeforeData,
                                            true
                                    )
                            )) {
                                is Result.Success -> {
                                    when (val resultUpdateAfterRoom =
                                            updateStatusRoomUserCase.execute(
                                                    Pair(
                                                            bookingEntity,
                                                            false
                                                    )
                                            )) {
                                        is Result.Success -> {
                                            editBookingRoomLiveData.postValue(result.data)
                                        }
                                        is Result.Fail -> {
                                            errorLiveData.postValue(resultUpdateAfterRoom.exception)
                                        }
                                    }
                                }
                                is Result.Fail -> {
                                    errorLiveData.postValue(resultUpdateBeforeRoom.exception)
                                }
                            }
                        } else {
                            editBookingRoomLiveData.postValue(result.data)
                        }
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }

    fun sortedItem(buildingNumber: String, roomNumber: String, roomEntity: MutableList<RoomEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val roomEntityDefault = roomEntity.filter {
                    it.roomNumber == roomNumber
                }
                val roomEntityEmpty = roomEntity.filter { it.status }

                var roomEntitySum = roomEntityDefault + roomEntityEmpty

                when (buildingNumber) {
                    "A", "B" -> {
                        roomEntitySum = roomEntitySum.sortedBy {
                            it.roomNumber.toInt()
                        }
                    }
                    "C" -> {
                        roomEntitySum = roomEntitySum.sortedBy {
                            it.roomNumber
                        }
                    }
                }

                sortedRoomNumberLiveData.postValue(roomEntitySum.toMutableList())
            }
        }
    }
}