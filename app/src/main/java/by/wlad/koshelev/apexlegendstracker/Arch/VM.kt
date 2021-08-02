package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class VM(private val apl: Application) : AndroidViewModel(apl) {
    companion object {
        lateinit var vm: VM
    }

    private val localModel: LocalModel = LocalModel()
    private val remoteModel: RemoteModel = RemoteModel(apl)

    var platformName: MutableLiveData<String> = MutableLiveData("origin") // платформа ЧекБоксов

    var loadFlag: MutableLiveData<Boolean> = MutableLiveData(false) // статус загрузки

    var gmsInet: MutableLiveData<GamerStats> = MutableLiveData(null) // текущий игрок из инета
    var gmsLocal: MutableLiveData<GamerStats> = MutableLiveData(null) // локаьлная стата выбранного игрока

    var gmsLists: MutableLiveData<MutableList<GamerStats>> = MutableLiveData(mutableListOf()) // список всех сохраненных юзеров


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
    private suspend fun getGmsFromInet(platform: String, nickName: String): GamerStats? = withContext(Dispatchers.Main) {
        val ret: GamerStats? = remoteModel.getGmsFromInet(platform, nickName)
        if (ret == null) loadFlag.value = null
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
    suspend fun saveGamer() = withContext(Dispatchers.Main) {
        if (gmsInet.value != null) {
            localModel.saveGamer(gmsInet.value!!)
            gmsLocal.value = getLocalGamer(gmsInet.value!!.primKey)
            getAllGamers()
        }
    }

    /**
     * удаление ОДНОГО игрока
     */
    // удаляем игрока. обновляем список всех. делаем проверку.
    fun deleteOneGamer(gamer: GamerStats, app: AppCompatActivity) {
        AlertDialog.Builder(app)
            .setCancelable(false)
            .setTitle(app.getString(R.string.suuure))
            .setMessage("${app.getString(R.string.deleteOneGamer)} ${gamer.data.platformInfo.platformUserId} ?")

            .setPositiveButton(app.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                MainScope().launch {
                    localModel.deleteOneGamer(gamer)
                    getAllGamers()
                    checkLocalGms()
                }

            })

            .setNegativeButton(app.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .create()
            .show()
    }


    /**
     * удаление ВСЕХ из бд.
     */
    // удаляем всех. обновляем всех. проверка.
    fun dellAllGamers(app: AppCompatActivity) {
        AlertDialog.Builder(app)
            .setCancelable(false)
            .setTitle(apl.getString(R.string.suuure))
            .setMessage(apl.getString(R.string.deleteAllGamers))

            .setPositiveButton(apl.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                MainScope().launch {
                    localModel.dellAllGamers()
                    getAllGamers()
                    checkLocalGms()
                }
            })

            .setNegativeButton(apl.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .create()
            .show()

    }


    /**
     * локальная проверка
     */
    // этот метод нужен  в методах удаления.
    // если мы удалим ТЕКУЩЕГО игрока, то в окне подробной статы останется "дата последенго сохранения".
    // этот метод исправляет этот баг. мы просто пытаемся вязть из БД игрока. а его там НЕТ ;)
    private suspend fun checkLocalGms() = withContext(Dispatchers.Main) {
        if (gmsInet.value != null) {
            gmsLocal.value = getLocalGamer(gmsInet.value!!.primKey)
        }
    }


}