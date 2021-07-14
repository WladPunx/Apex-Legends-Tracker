package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VM(private val apl: Application) : AndroidViewModel(apl) {
    companion object {
        lateinit var vm: VM
    }

    private val localModel: LocalModel = LocalModel()
    private val remoteModel: RemoteModel = RemoteModel(apl)


    var platformName: MutableLiveData<String> = MutableLiveData("origin")

    var gmsInet: MutableLiveData<_GamerStats> = MutableLiveData(null)


    fun getGms(platform: String, nickName: String) {
        getGmsFromInet(platform, nickName)
    }


    private fun getGmsFromInet(platform: String, nickName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            gmsInet.postValue(remoteModel.getGmsFromInet(platform, nickName))
        }
    }

}