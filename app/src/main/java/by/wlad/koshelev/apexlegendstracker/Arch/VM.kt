package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class VM(private val apl: Application) : AndroidViewModel(apl) {
    companion object {
        lateinit var vm: VM
    }

    private val repository: Repository = Repository(apl)

    var platformName: MutableLiveData<String> = MutableLiveData("origin") // платформа ЧекБоксов

    var loadFlag: MutableLiveData<Boolean> = MutableLiveData(false) // статус загрузки

    var gmsInet: MutableLiveData<GamerStats> = MutableLiveData(null) // текущий игрок из инета
    var gmsLocal: MutableLiveData<GamerStats> = MutableLiveData(null) // локаьлная стата выбранного игрока

    var gmsLists: MutableLiveData<MutableList<GamerStats>> = MutableLiveData(mutableListOf()) // список всех сохраненных юзеров


    suspend fun getGms(platform: String, nickName: String) = withContext(Dispatchers.Main) {
        repository.getGms(platform, nickName)
    }

    suspend fun getAllGamers() = withContext(Dispatchers.IO) {
        repository.getAllGamers()
    }

    suspend fun saveGamer() = withContext(Dispatchers.Main) {
        repository.saveGamer()
    }


    suspend fun deleteOneGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
        repository.deleteOneGamer(gamer)
    }

    suspend fun dellAllGamers() = withContext(Dispatchers.IO) {
        repository.dellAllGamers()
    }


}