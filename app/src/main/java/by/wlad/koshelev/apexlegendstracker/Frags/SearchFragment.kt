package by.wlad.koshelev.apexlegendstracker.Frags

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.CheckBoxFromSearchFrag
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO временный блок!!
        VM.vm.getGms("origin", "XaXoLOL")

        CheckBoxFromSearchFrag.create(activity as AppCompatActivity, this)

        /**
         * кнопка запроса данных
         */
        findGamer_btn_SerachFrag.setOnClickListener {
            val name: String = nickName_edt_SearchFrag.text.toString()
            val platform: String = VM.vm.platformName.value!!
            if (name != null && name != "") {
                VM.vm.getGms(platform, name)
                it.hideKeyboard()
            }
        }


        /**
         * слушатель на данные из инета
         */
        VM.vm.gmsInet.observe(viewLifecycleOwner, Observer {
            Log.e("!!!", "${VM.vm.gmsInet.value}")
        })


    }


    // спрятать клаву
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}