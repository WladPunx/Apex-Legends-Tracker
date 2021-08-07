package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val apl: Application) {


    private val localModel: LocalModel = LocalModel()
    private val remoteModel: RemoteModel = RemoteModel(apl)


    /**
     * основной запрос на Игрока. сразу из всех мест берем.
     * так же есть Флаг на "статус" выполнения операции loadFlag
     * true = работает. false = выполнено успешно. null = ошибка
     * перед запросами обнуляем наши данные GMS
     */
    suspend fun getGms(platform: String, nickName: String) = withContext(Dispatchers.Main) {
        VM.vm.loadFlag.value = true

        VM.vm.gmsInet.value = null
        VM.vm.gmsLocal.value = null

        VM.vm.gmsLocal.value = getLocalGamer("${nickName}_${platform}")
        VM.vm.gmsInet.value = getGmsFromInet(platform, nickName)

        if (VM.vm.loadFlag.value != null) VM.vm.loadFlag.value = false
    }


    /**
     * запрос данных из инета
     */
    // берем данные из инета. если была ошибка - данные будут null
    // если данные null - то и наш ФлагСтатус тоже null
    private suspend fun getGmsFromInet(platform: String, nickName: String): GamerStats? = withContext(Dispatchers.Main) {
        val ret: GamerStats? = remoteModel.getGmsFromInet(platform, nickName)
        if (ret == null) VM.vm.loadFlag.value = null
        return@withContext ret
    }


    /**
     * запрос локальных данных
     */
    private suspend fun getLocalGamer(id: String): GamerStats = withContext(Dispatchers.IO) {
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
        val list: MutableList<GamerStats> = localModel.getAllGamers()

        withContext(Dispatchers.Default) {
            VM.vm.gmsLists.value?.clear()
            VM.vm.gmsLists.value?.addAll(list)
        }
        withContext(Dispatchers.Main) {
            VM.vm.gmsLists.value = VM.vm.gmsLists.value
        }
    }

    /**
     * сохранить игрока
     */
    // сохраняем игрока в БД
    // сразу же берем новые данные на него (для статуса "последнее сохранение")
    // обновляем список ВСЕХ игроков
    suspend fun saveGamer() = withContext(Dispatchers.Main) {
        if (VM.vm.gmsInet.value != null) {
            localModel.saveGamer(VM.vm.gmsInet.value!!)
            VM.vm.gmsLocal.value = getLocalGamer(VM.vm.gmsInet.value!!.primKey)
            getAllGamers()
        }
    }

    /**
     * удаление ОДНОГО игрока
     */
    // удаляем игрока. обновляем список всех. делаем проверку.
    suspend fun deleteOneGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
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
    // этот метод нужен  в методах удаления.
    // если мы удалим ТЕКУЩЕГО игрока, то в окне подробной статы останется "дата последенго сохранения".
    // этот метод исправляет этот баг. мы просто пытаемся вязть из БД игрока. а его там НЕТ ;)
    private suspend fun checkLocalGms() = withContext(Dispatchers.Main) {
        if (VM.vm.gmsInet.value != null) {
            VM.vm.gmsLocal.value = getLocalGamer(VM.vm.gmsInet.value!!.primKey)
        }
    }

}