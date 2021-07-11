package by.wlad.koshelev.apexlegendstracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GamerStatsDataBase.create(this)

        val navContr =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        navContr.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("!!!", "${destination.id}")
        }


        // дизайн и навигация основных кнопок управления Фрагментами
        NavigateButton.create(this)

        CoroutineScope(Dispatchers.IO).launch {
            val a: _GamerStats = ApiGamerStats.create().getGamerStats("origin", "XaXoLOL")
            GamerStatsDataBase.dao.dellAll()
            GamerStatsDataBase.dao.addNew(a)
            GamerStatsDataBase.dao.addNew(a)
            val b: MutableList<_GamerStats> = GamerStatsDataBase.dao.getAll()
            Log.e("!!!", "${b[0]}")
            Log.e("!!!", "${b[1]}")
            Log.e("!!!", "${b[0].data.segments[0].stats.kills.value}")

        }


    }


}