package by.wlad.koshelev.apexlegendstracker.UI.SearchFrag

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
import by.wlad.koshelev.apexlegendstracker.DebugDo
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.UI.CoolerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    // поток для отображения статистики
    var statsScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        statsScope.cancel()
        MainScope().launch {
            VM.vm.loadFlag.value = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DebugDo {
            nickName_edt_SearchFrag.setText("daltoosh")
        }

        // ЧекБоксы и их логика
        CheckBoxFromSearchFrag.create(activity as AppCompatActivity, this)

        /**
         * кнопка запроса данных
         */
        findGamer_btn_SerachFrag.setOnClickListener {
            val name: String = nickName_edt_SearchFrag.text.toString()
            val platform: String = VM.vm.platformName.value!!
            if (name != null && name != "") {
                statsScope.cancel()
                statsScope = MainScope()
                statsScope.launch {
                    VM.vm.getGms(platform, name)
                }
                it.hideKeyboard()
            }
        }

        /**
         * кнопка сохранения игрока
         */
        saveGamer_btn_SearchFrag.setOnClickListener {
            statsScope.launch {
                VM.vm.saveGamer()
            }
        }


        /**
         * слушатель на "последняя дата сохранения"
         */
        VM.vm.gmsLocal.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                saverStatus_txt_SearchFrag.setText("${CoolerView.getCoolerDate(it, activity as AppCompatActivity)}")
            } else {
                saverStatus_txt_SearchFrag.setText("")
            }
        })


        /**
         * слушатель статуса загрузки
         */
        VM.vm.loadFlag.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                loads_status_txt_SearchFragment.setText("${getString(R.string.load_error)}")
                loads_status_txt_SearchFragment.visibility = View.VISIBLE
            } else if (it) {
                loads_status_txt_SearchFragment.setText("${getString(R.string.load_status)}")
                loads_status_txt_SearchFragment.visibility = View.VISIBLE
            } else {
                loads_status_txt_SearchFragment.visibility = View.INVISIBLE
            }
        })


        /**
         * слушатель на данные из инета
         */
        VM.vm.gmsInet.observe(viewLifecycleOwner, Observer {
            statsScope.launch {
                if (it != null) {

                    /**
                     * основной слушатель изменений для текущего Фрагмента
                     */
                    statsScope.launch {
                        SearchFragListener.setView(this@SearchFragment)
                    }


                    // показываем разметку со статой
                    StatsLayout_SearcgFrag.visibility = View.VISIBLE
                    StatsLayout_SearcgFrag.alpha = 0f
                    StatsLayout_SearcgFrag.animate()
                        .setDuration(500L)
                        .alpha(1f)

                    // если у нас NULL - спрячем разметку со статой

                } else {
                    StatsLayout_SearcgFrag.animate()
                        .setDuration(500L)
                        .alpha(0f)
                        .withEndAction {
                            try {
                                StatsLayout_SearcgFrag.alpha = 1f
                                StatsLayout_SearcgFrag.visibility = View.GONE
                            } catch (ex: Exception) {
                                Log.e("!!ERROR!!", "${ex}  косяк задержки анимации ")
                            }

                        }
                }

            }
        })


    }


    /**
     * спрятать клаву
     */
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}