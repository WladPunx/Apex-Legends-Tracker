package by.wlad.koshelev.apexlegendstracker.Arch

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


object SharedPref {

    lateinit var myShar: SharedPreferences
    fun create(apl: Application) {
        myShar = apl.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
    }

    private val style = "style"
    fun getStyle(): Boolean = myShar.getBoolean(style, false)
    fun setStyle(value: Boolean) {
        myShar.edit().putBoolean(style, value).apply()
    }


    private val lang = "lang"
    fun getLang(): String = myShar.getString(lang, "ru") ?: "ru"
    fun setLang(lang: String) {
        myShar.edit().putString(SharedPref.lang, lang).apply()
    }


}