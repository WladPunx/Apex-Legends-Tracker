package by.wlad.koshelev.apexlegendstracker.UI.MainActiv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.wlad.koshelev.apexlegendstracker.Arch.SharedPref
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // ШаредПреф
        SharedPref.myShar = getSharedPreferences(SharedPref.APP_PREFERENCES, MODE_PRIVATE)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // включение БД
        GamerStatsDataBase.create(this)

        // поключение ViewModel
        VM.vm = ViewModelProvider(this).get(VM::class.java)

        // дизайн и навигация основных кнопок управления Фрагментами
        NavigateButton.create(this)

        // Дровер
        DrawerSettings.set(this)

        // запрос в БД
        MainScope().launch {
            VM.vm.getAllGamers()
        }


        //TODO временный блок!!
        MainScope().launch {
            VM.vm.getGms("origin", "tonyd221")
        }


    }


}