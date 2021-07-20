package by.wlad.koshelev.apexlegendstracker.UI.MainActiv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // включение моей БД
        GamerStatsDataBase.create(this)

        // поключение ViewModel
        VM.vm = ViewModelProvider(this).get(VM::class.java)

        // дизайн и навигация основных кнопок управления Фрагментами
        NavigateButton.create(this)

        MainScope().launch {
            VM.vm.getAllGamers()
        }

        //TODO временный блок!!
        MainScope().launch {
            VM.vm.getGms("origin", "tonyd221")
        }


    }


}