package th.co.theroom.shareviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import th.co.theroom.base.BaseViewModel
import th.co.theroom.model.BookingEntity

class ShareViewModel : BaseViewModel() {
    val changeFragmentLiveData: MutableLiveData<String> = MutableLiveData()
    val sendDataFragmentLiveData: MutableLiveData<BookingEntity> = MutableLiveData()
    val changeTitleBarLiveData: MutableLiveData<String> = MutableLiveData()

    fun changeFragment(tag: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                changeFragmentLiveData.postValue(tag)
            }
        }
    }

    fun sendBookingDetailFragment(bookingEntity: BookingEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                sendDataFragmentLiveData.postValue(bookingEntity)
            }
        }
    }

    fun changeTitleBar(value :String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                changeTitleBarLiveData.postValue(value)
            }
        }
    }
}