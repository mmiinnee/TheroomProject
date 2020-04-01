package th.co.theroom.bookingdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import th.co.theroom.base.BaseViewModel
import th.co.theroom.model.BookingEntity
import th.co.theroom.model.Result
import th.co.theroom.usecase.CancelBookingUserCase
import th.co.theroom.usecase.CheckInUserCase
import th.co.theroom.usecase.SelectBookingListUseCase
import th.co.theroom.usecase.UpdateStatusRoomUserCase

class BookingDataFragmentViewModel(private val selectBookingListUseCase: SelectBookingListUseCase,
                                   private val cancelBookingUserCase: CancelBookingUserCase,
                                   private val updateStatusRoomUserCase: UpdateStatusRoomUserCase,
                                   private val checkInUserCase: CheckInUserCase) : BaseViewModel() {

    val getBookingListLiveData: MutableLiveData<MutableList<BookingEntity>> = MutableLiveData()
    val deleteBookingLiveData: MutableLiveData<Unit> = MutableLiveData()
    val checkInLiveData: MutableLiveData<Unit> = MutableLiveData()

    fun getBookingList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = selectBookingListUseCase.execute(Unit)) {
                    is Result.Success -> {
                        val bookingEntityFilter = result.data.filter { it.status != "ยกเลิก" }
                        getBookingListLiveData.postValue(bookingEntityFilter.toMutableList())
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }

    fun updateCheckIn(bookingEntity: BookingEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = checkInUserCase.execute(bookingEntity)) {
                    is Result.Success -> {
                        checkInLiveData.postValue(Unit)
                    }
                    is Result.Fail -> {
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }

    fun cancelBooking(bookingEntity: BookingEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = cancelBookingUserCase.execute(bookingEntity)) {
                    is Result.Success -> {
                        when (val resultUpdateStatus = updateStatusRoomUserCase.execute(Pair(bookingEntity, true))) {
                            is Result.Success -> {
                                deleteBookingLiveData.postValue(Unit)
                            }
                            is Result.Fail -> {
                                errorLiveData.postValue(resultUpdateStatus.exception)
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