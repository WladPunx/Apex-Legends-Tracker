package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalModel(
    private val apl: Application
) {

    private val bd = GamerStatsDataBase.create(apl).getStatsDAO()

    suspend fun getLocalGamer(id: String): GamerStats = withContext(Dispatchers.IO) {
        return@withContext bd.getGamer(id)
    }

    suspend fun saveGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
        try {
            bd.addNew(gamer)
        } catch (ex: Exception) {
            bd.update(gamer)
        }
    }

    suspend fun getAllGamers(): MutableList<GamerStats> = withContext(Dispatchers.IO) {
        return@withContext bd.getAllGamers()
    }

    suspend fun deleteOneGamer(gamer: GamerStats) = withContext(Dispatchers.IO) {
        bd.deleteOne(gamer)
    }


    suspend fun dellAllGamers() = withContext(Dispatchers.IO) {
        bd.dellAll()
    }


}