package th.co.theroom.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData : MutableLiveData<String> = MutableLiveData()
}