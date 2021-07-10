package by.wlad.koshelev.apexlegendstracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navContr =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        navContr.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.e("!!!", "${destination.id}")
        }


        // дизайн и навигация основных кнопок управления Фрагментами
        NavigateButton.create(this)


    }


}