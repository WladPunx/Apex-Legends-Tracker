package by.wlad.koshelev.apexlegendstracker.Frags

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.CheckBoxFromSearchFrag
import by.wlad.koshelev.apexlegendstracker.GamerStats.Segment
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import by.wlad.koshelev.apexlegendstracker.LegendsAdapter
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.SetImgFromInet
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


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

        // ЧекБоксы и их логика
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
            MainScope().launch {
                if (VM.vm.gmsInet.value != null) {

                    // АПИШка ппц какая кривая. не у всех игроков могут быть одни и теже данные.
                    // поэтому будет оборачиватье в Try-Catch обращение к Массивам со статой

                    // данные статистики. все + общие из LifeTime
                    val curentStats: _GamerStats = VM.vm.gmsInet.value!!
                    val _lifeTimeStat: MutableList<Segment> = curentStats.data.segments
                        .asSequence()
                        .filter { it.metadata.name == "Lifetime" }
                        .toMutableList()
                    val lifeTimeStat: Segment = _lifeTimeStat[0]

                    /**
                     * СТАТАЫ КАЖДОЙ ЛЕГЕНДЫ
                     */
                    val legendsList: MutableList<Segment> = curentStats.data.segments
                        .asSequence()
                        .filter { it.metadata.name != "Lifetime" }
                        .toMutableList()
                    legendsRecycler.layoutManager = GridLayoutManager(activity, 1)
                    legendsRecycler.adapter = LegendsAdapter(activity as AppCompatActivity, legendsList)

                    /**
                     * ОБЩАЯ СТАТА АККА
                     */


                    // имя и платформа
                    gamerName_tx_SerachFrag.setText("${curentStats.data.platformInfo.platformUserId}")
                    platform_tx_SerachFrag.setText("${curentStats.data.platformInfo.platformSlug}")

                    // аватар
                    SetImgFromInet.set(curentStats.data.platformInfo.avatarUrl, avatar_img_SerachFrag)

                    // ранг
                    SetImgFromInet.set(lifeTimeStat.stats.rankScore.metadata.iconUrl, rank_img_SerachFrag)


                    // уровень акка
                    try {
                        level_txt_SerachFrag.setText(
                            "${lifeTimeStat.stats.level.displayValue} ${
                                getString(
                                    R.string.level
                                )
                            }"
                        )
                    } catch (e: Exception) {
                        level_txt_SerachFrag.setText("n/a ${getString(R.string.level)}")
                    }


                    //  убийства
                    try {
                        kills_txt_SerachFrag.setText("${getString(R.string.kills)} ${lifeTimeStat.stats.kills.displayValue}")
                    } catch (ex: java.lang.Exception) {
                        kills_txt_SerachFrag.setText("${getString(R.string.kills)} n/a")
                    }

                    // победы
                    try {
                        wins_txt_SerachFrag.setText("${getString(R.string.wins)}  ${lifeTimeStat.stats.winsWithFullSquad.displayValue}")
                    } catch (ex: java.lang.Exception) {
                        wins_txt_SerachFrag.setText("${getString(R.string.wins)} n/a")
                    }


                    // топ3
                    try {
                        top3_txt_SerachFrag.setText("${getString(R.string.top3)}  ${lifeTimeStat.stats.timesPlacedtop3.displayValue}")
                    } catch (ex: java.lang.Exception) {
                        top3_txt_SerachFrag.setText("${getString(R.string.top3)}  n/a")
                    }


                }
            }
        })


    }


    // спрятать клаву
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}