package by.wlad.koshelev.apexlegendstracker.Arch

import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalModel {

    suspend fun getLocalGamer(id: String): GamerStats = withContext(Dispatchers.IO) {
        return@withContext GamerStatsDataBase.dao.getGamer(id)
    }

    suspend fun saveGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
        try {
            GamerStatsDataBase.dao.addNew(gamer)
        } catch (ex: Exception) {
            GamerStatsDataBase.dao.update(gamer)
        }
    }

    suspend fun getAllGamers(): MutableList<GamerStats> = withContext(Dispatchers.IO) {
        return@withContext GamerStatsDataBase.dao.getAllGamers()
    }

    suspend fun deleteOneGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
        GamerStatsDataBase.dao.deleteOne(gamer)
    }


    suspend fun dellAllGamers() = withContext(Dispatchers.IO){
        GamerStatsDataBase.dao.dellAll()
    }


}