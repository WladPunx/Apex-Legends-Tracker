package by.wlad.koshelev.apexlegendstracker.Arch

import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalModel {

    suspend fun getLocalGamer(id: String): _GamerStats = withContext(Dispatchers.IO) {
        return@withContext GamerStatsDataBase.dao.getGamer(id)
    }


    suspend fun saveGamer(gamer: _GamerStats) = withContext(Dispatchers.IO) {
        try {
            GamerStatsDataBase.dao.addNew(gamer)
        } catch (ex: Exception) {
            GamerStatsDataBase.dao.update(gamer)
        }

    }


}