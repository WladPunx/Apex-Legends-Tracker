package by.wlad.koshelev.apexlegendstracker.sharedpref

import android.app.Application
import android.content.Context


class SharedPref(
    private val apl: Application
) {
    private val data = apl.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)

    var style: Boolean by PreferencesDelegate(data, "style", false)
    var lang: String by PreferencesDelegate(data, "lang", "ru")
}