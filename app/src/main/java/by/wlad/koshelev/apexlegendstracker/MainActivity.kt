package by.wlad.koshelev.apexlegendstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStatsDataBase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // включение моей БД
        GamerStatsDataBase.create(this)

        VM.vm = ViewModelProvider(this).get(VM::class.java)

        // дизайн и навигация основных кнопок управления Фрагментами
        NavigateButton.create(this)

        //TODO временный блок!!
        VM.vm.getGms("origin", "tonyd221")


    }


}