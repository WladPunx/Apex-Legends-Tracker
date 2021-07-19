package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import android.widget.Toast
import by.wlad.koshelev.apexlegendstracker.GamerStats._ApiGamerStats
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteModel(private val apl: Application) {

    suspend fun getGmsFromInet(platform: String, nickName: String): _GamerStats? =
        withContext(Dispatchers.Main) {
            var ret: _GamerStats? = null
            try {

                withContext(Dispatchers.IO) {
                    ret = _ApiGamerStats.create().getGamerStats(platform, nickName)
                    ret?.inz()
                }


            } catch (ex: retrofit2.HttpException) {
                Toast.makeText(
                    apl,
                    "${platform} : ${nickName}" +
                            "\n" +
                            "${apl.getString(R.string.error_nickname)} ",
                    Toast.LENGTH_SHORT
                ).show()


            } catch (ex: Exception) {
                Toast.makeText(apl, apl.getString(R.string.error_inet), Toast.LENGTH_SHORT)
                    .show()
            }

            return@withContext ret
        }

}