package by.wlad.koshelev.apexlegendstracker

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import kotlinx.android.synthetic.main.fragment_search.*

object CheckBoxFromSearchFrag {

    private val ORIGIN: String = "origin"
    private val PSN: String = "psn"
    private val XBOX: String = "xbox"


    fun create(app: AppCompatActivity, frag: Fragment) {


        app.origin_checkBox_SearchFrag.setOnClickListener {
            VM.vm.platformName.postValue(ORIGIN)
        }

        app.psn_checkBox_SearchFrag.setOnClickListener {
            VM.vm.platformName.postValue(PSN)
        }

        app.xbox_checkBox_SearchFrag.setOnClickListener {
            VM.vm.platformName.postValue(XBOX)
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
                    app.origin_checkBox_SearchFrag.isChecked = true
                    app.origin_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.origin))
                }
                PSN -> {
                    app.psn_checkBox_SearchFrag.isChecked = true
                    app.psn_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.playstaion))
                }
                XBOX -> {
                    app.xbox_checkBox_SearchFrag.isChecked = true
                    app.xbox_checkBox_SearchFrag.setTextColor(ContextCompat.getColor(app, R.color.xbox))
                }
            }
        })

    }


}