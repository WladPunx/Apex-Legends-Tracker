package by.wlad.koshelev.apexlegendstracker.UI.SearchFrag

import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object CheckBoxFromSearchFrag {

    private val ORIGIN: String = "origin"
    private val PSN: String = "psn"
    private val XBOX: String = "xbox"


    fun create(app: AppCompatActivity, frag: SearchFragment) {

        app.origin_checkBox_SearchFrag.setOnClickListener {
            set(ORIGIN)
        }

        app.psn_checkBox_SearchFrag.setOnClickListener {
            set(PSN)
        }

        app.xbox_checkBox_SearchFrag.setOnClickListener {
            set(XBOX)
        }


        VM.vm.platformName.observe(frag.viewLifecycleOwner, Observer {

            app.origin_checkBox_SearchFrag.isChecked = false
            app.psn_checkBox_SearchFrag.isChecked = false
            app.xbox_checkBox_SearchFrag.isChecked = false

            app.origin_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.default_checkBox))
            app.psn_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.default_checkBox))
            app.xbox_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.default_checkBox))

            when (VM.vm.platformName.value) {
                ORIGIN -> {
                    setColorCheckBox(app, app.origin_checkBox_SearchFrag, R.color.origin)
                }
                PSN -> {
                    setColorCheckBox(app, app.psn_checkBox_SearchFrag, R.color.playstaion)
                }
                XBOX -> {
                    setColorCheckBox(app, app.xbox_checkBox_SearchFrag, R.color.xbox)
                }
            }
        })

    }


    private fun setColorCheckBox(app: AppCompatActivity, view: CheckBox, color: Int) {
        view.isChecked = true
        view.setTextColor(ContextCompat.getColor(app, color))
        app.nickName_edt_SearchFrag.setTextColor(ContextCompat.getColor(app, color))
    }

    private fun set(platform: String) {
        MainScope().launch {
            VM.vm.platformName.value = platform
        }
    }


}