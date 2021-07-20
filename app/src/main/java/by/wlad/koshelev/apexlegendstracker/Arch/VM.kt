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

    var loadFlag: MutableLiveData<Boolean> = MutableLiveData(false) // статус загрузки

    var gmsInet: MutableLiveData<_GamerStats> = MutableLiveData(null) // текущий игрок из инета
    var gmsLocal: MutableLiveData<_GamerStats> = MutableLiveData(null) // локаьлная стата выбранного игрока

    var gmsLists: MutableLiveData<MutableList<_GamerStats>> = MutableLiveData(mutableListOf()) // список всех сохраненных юзеров


    /**
     * основной запрос на Игрока. сразу из всех мест берем.
     * так же есть Флаг на "статус" выполнения операции loadFlag
     * true = работает. false = выполнено успешно. null = ошибка
     * перед запросами обнуляем наши данные GMS
     */
    suspend fun getGms(platform: String, nickName: String) = withContext(Dispatchers.Main) {
        loadFlag.value = true

        gmsInet.value = null
        gmsLocal.value = null

        gmsLocal.value = getLocalGamer("${nickName}_${platform}")
        gmsInet.value = getGmsFromInet(platform, nickName)

        if (loadFlag.value != null) loadFlag.value = false
    }


    /**
     * запрос данных из инета
     */
    // берем данные из инета. если была ошибка - данные будут null
    // если данные null - то и наш ФлагСтатус тоже null
    private suspend fun getGmsFromInet(platform: String, nickName: String): _GamerStats? = withContext(Dispatchers.Main) {
        val ret: _GamerStats? = remoteModel.getGmsFromInet(platform, nickName)
        if (ret == null) loadFlag.value = null
        return@withContext ret
    }


    /**
     * запрос локальных данных
     */
    private suspend fun getLocalGamer(id: String): _GamerStats = withContext(Dispatchers.IO) {
        return@withContext localModel.getLocalGamer(id)
    }

    /**
     *
     *
     *
     *
     *
     *
     */


    /**
     * получение всех игроков из БД
     */
    suspend fun getAllGamers() = withContext(Dispatchers.IO) {
        val list: MutableList<_GamerStats> = localModel.getAllGamers()

        withContext(Dispatchers.Default) {
            gmsLists.value?.clear()
            gmsLists.value?.addAll(list)
        }
        withContext(Dispatchers.Main) {
            gmsLists.value = gmsLists.value
        }
    }

    /**
     * сохранить игрока
     */
    // сохраняем игрока в БД
    // сразу же берем новые данные на него (для статуса "последнее сохранение")
    // обновляем список ВСЕХ игроков
    suspend fun saveGamer(gamer: _GamerStats) = withContext(Dispatchers.Main) {
        localModel.saveGamer(gamer)
        gmsLocal.value = getLocalGamer(gamer.primKey)
        getAllGamers()
    }

    /**
     * удаление ОДНОГО игрока
     */
    // удаляем игрока. обновляем список всех. делаем проверку.
    suspend fun deleteOneGamer(gamer: _GamerStats) = withContext(Dispatchers.IO) {
        localModel.deleteOneGamer(gamer)
        getAllGamers()
        checkLocalGms()
    }


    /**
     * удаление ВСЕХ из бд.
     */
    // удаляем всех. обновляем всех. проверка.
    suspend fun dellAllGamers() = withContext(Dispatchers.IO) {
        localModel.dellAllGamers()
        getAllGamers()
        checkLocalGms()
    }


    /**
     * локальная проверка
     */
    // этот метод нужно в методах удаления.
    // если мы удалим ТЕКУЩЕГО игрока, то в окне подробной статы останется "дата последенго сохранения".
    // этот метод исправляет этот баг
    private suspend fun checkLocalGms() = withContext(Dispatchers.Main) {
        if (gmsInet.value != null) {
            gmsLocal.value = getLocalGamer(gmsInet.value!!.primKey)
        }
    }


}