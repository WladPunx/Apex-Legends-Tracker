package by.wlad.koshelev.apexlegendstracker.UI

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import java.util.*

object CoolerView {

    /**
     * клевая ДАТА
     */
    fun getCoolerDate(gamer: GamerStats, app: AppCompatActivity): String {
        val currentDate: Long = Date().time
        val oldDate: Long = gamer.dateInfo.toLong()
        val minutes: Int = ((currentDate - oldDate) / 1000 / 60).toInt()
        val hours: Int = minutes / 60
        val days: Int = hours / 24

        return if (minutes < 60) "${minutes} ${app.getString(R.string.minutes)} ${app.getString(R.string.ago)}"
        else if (hours < 24) "${hours} ${app.getString(R.string.hours)} ${app.getString(R.string.ago)}"
        else "${days} ${app.getString(R.string.day)} ${app.getString(R.string.ago)}"
    }


    /**
     * клевое имя игрока с цветом платформы
     */
    fun setNameWithColor(app: AppCompatActivity, view: TextView, gms: GamerStats) {
        view.setText("${gms.data.platformInfo.platformUserId}")
        when (gms.data.platformInfo.platformSlug) {
            "origin" -> view.setTextColor(ContextCompat.getColor(app, R.color.origin))
            "psn" -> view.setTextColor(ContextCompat.getColor(app, R.color.playstaion))
            "xbox" -> view.setTextColor(ContextCompat.getColor(app, R.color.xbox))
        }
    }
}