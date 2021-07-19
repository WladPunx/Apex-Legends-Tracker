package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VM(private val apl: Application) : AndroidViewModel(apl) {
    companion object {
        lateinit var vm: VM
    }

    private val localModel: LocalModel = LocalModel()
    private val remoteModel: RemoteModel = RemoteModel(apl)


    var platformName: MutableLiveData<String> = MutableLiveData("origin") // платформа ЧекБоксов
    var gmsInet: MutableLiveData<_GamerStats> = MutableLiveData(null) // текущий игрок из инета
    var loadFlag: MutableLiveData<Boolean> = MutableLiveData(false) // статус загрузки


    suspend fun getGms(platform: String, nickName: String) = withContext(Dispatchers.Default) {
        loadFlag.postValue(true)

        gmsInet.postValue(null)

        gmsInet.postValue(getGmsFromInet(platform, nickName))

        if (loadFlag.value != null) loadFlag.postValue(false)

    }


    private suspend fun getGmsFromInet(platform: String, nickName: String): _GamerStats? = withContext(Dispatchers.IO) {
        val ret: _GamerStats? = remoteModel.getGmsFromInet(platform, nickName)
        if (ret == null) loadFlag.postValue(null)
        return@withContext remoteModel.getGmsFromInet(platform, nickName)
    }

}