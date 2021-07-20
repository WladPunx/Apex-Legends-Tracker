package by.wlad.koshelev.apexlegendstracker.UI

import androidx.appcompat.app.AppCompatActivity
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import java.util.*

object CoolerDate {

    fun getCoolerDate(gamer: _GamerStats, app: AppCompatActivity): String {
        val currentDate: Long = Date().time
        val oldDate: Long = gamer.dateInfo.toLong()
        val minutes: Int = ((currentDate - oldDate) / 1000 / 60).toInt()
        val hours: Int = minutes / 60
        val days: Int = hours / 24

        return if (minutes < 60) "${minutes} ${app.getString(R.string.minutes)} ${app.getString(R.string.ago)}"
        else if (hours < 24) "${hours} ${app.getString(R.string.hours)} ${app.getString(R.string.ago)}"
        else "${days} ${app.getString(R.string.day)} ${app.getString(R.string.ago)}"
    }
}