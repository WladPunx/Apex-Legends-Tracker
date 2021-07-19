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
    var gmsLocal: MutableLiveData<_GamerStats> = MutableLiveData(null)


    suspend fun getGms(platform: String, nickName: String) = withContext(Dispatchers.Default) {
        loadFlag.postValue(true)

        gmsInet.postValue(null)
        gmsLocal.postValue(null)

        gmsLocal.postValue(getLocalGamer("${nickName}_${platform}"))
        gmsInet.postValue(getGmsFromInet(platform, nickName))

        if (loadFlag.value != null) loadFlag.postValue(false)

    }


    private suspend fun getGmsFromInet(platform: String, nickName: String): _GamerStats? = withContext(Dispatchers.IO) {
        val ret: _GamerStats? = remoteModel.getGmsFromInet(platform, nickName)
        if (ret == null) loadFlag.postValue(null)
        return@withContext ret
    }

    private suspend fun getLocalGamer(id: String): _GamerStats = withContext(Dispatchers.IO) {
        return@withContext localModel.getLocalGamer(id)
    }

    suspend fun saveGamer(gamer: _GamerStats) = withContext(Dispatchers.IO) {
        localModel.saveGamer(gamer)
        gmsLocal.postValue(getLocalGamer(gamer.primKey))
    }

}