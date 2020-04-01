package th.co.theroom.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import th.co.theroom.base.BaseViewModel
import th.co.theroom.model.Result
import th.co.theroom.model.RoomModel
import th.co.theroom.usecase.InsertRoomUserCase

class SplashScreenViewModel(private val insertRoomUserCase: InsertRoomUserCase) : BaseViewModel() {

    val insertRoomLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun insertRoom(jsonRoom: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadingLiveData.postValue(true)
                val roomModel = Gson().fromJson(jsonRoom, RoomModel::class.java)

                when (val result = insertRoomUserCase.execute(roomModel)) {
                    is Result.Success -> {
                        loadingLiveData.postValue(false)
                        insertRoomLiveData.postValue(result.data)
                    }
                    is Result.Fail -> {
                        loadingLiveData.postValue(false)
                        errorLiveData.postValue(result.exception)
                    }
                }
            }
        }
    }
}