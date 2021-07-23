package by.wlad.koshelev.apexlegendstracker.UI.SearchFrag

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.Segment
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.UI.CoolerView
import by.wlad.koshelev.apexlegendstracker.UI.SetImgFromInet
import kotlinx.android.synthetic.main.fragment_search.*

object SearchFragListener {

    fun setView(frag: SearchFragment) {

        // АПИШка ппц какая кривая. не у всех игроков могут быть одни и теже данные.
        // поэтому будем оборачивать в Try-Catch обращения к Массивам со статой

        // данные статистики. все + общие из LifeTime
        val curentStats: _GamerStats = VM.vm.gmsInet.value!!
        val _lifeTimeStat: MutableList<Segment> = curentStats.data.segments
            .asSequence()
            .filter { it.metadata.name == "Lifetime" }
            .toMutableList()
        val lifeTimeStat: Segment = _lifeTimeStat[0]

        /**
         * СТАТАЫ КАЖДОЙ ЛЕГЕНДЫ помещаем в Ресайкл
         */
        val legendsList: MutableList<Segment> = curentStats.data.segments
            .asSequence()
            .filter { it.metadata.name != "Lifetime" }
            .toMutableList()
        frag.legendsRecycler.layoutManager = GridLayoutManager(frag.activity, 1)
        frag.legendsRecycler.adapter = LegendsAdapter(frag.activity as AppCompatActivity, legendsList)


        /**
         * ОБЩАЯ СТАТА АККА
         */


        // имя и платформа и цвет
        CoolerView.setNameWithColor(frag.activity as AppCompatActivity, frag.gamerName_tx_SerachFrag, curentStats)

        // аватар
        SetImgFromInet.set(curentStats.data.platformInfo.avatarUrl, frag.avatar_img_SerachFrag)

        // ранг
        try {
            SetImgFromInet.set(lifeTimeStat.stats.rankScore.metadata.iconUrl, frag.rank_img_SerachFrag)
        } catch (e: Exception) {
        }


        // уровень акка
        try {
            frag.level_txt_SerachFrag.setText(
                "${lifeTimeStat.stats.level.displayValue} ${
                    frag.getString(
                        R.string.level
                    )
                }"
            )
        } catch (e: Exception) {
            frag.level_txt_SerachFrag.setText("n/a ${frag.getString(R.string.level)}")
        }


        //  убийства
        try {
            frag.kills_txt_SerachFrag.setText("${frag.getString(R.string.kills)} ${lifeTimeStat.stats.kills.displayValue}")
        } catch (ex: java.lang.Exception) {
            frag.kills_txt_SerachFrag.setText("${frag.getString(R.string.kills)} n/a")
        }

        // победы
        try {
            frag.wins_txt_SerachFrag.setText("${frag.getString(R.string.wins)}  ${lifeTimeStat.stats.winsWithFullSquad.displayValue}")
        } catch (ex: java.lang.Exception) {
            frag.wins_txt_SerachFrag.setText("${frag.getString(R.string.wins)} n/a")
        }


        // топ3
        try {
            frag.top3_txt_SerachFrag.setText("${frag.getString(R.string.top3)}  ${lifeTimeStat.stats.timesPlacedtop3.displayValue}")
        } catch (ex: java.lang.Exception) {
            frag.top3_txt_SerachFrag.setText("${frag.getString(R.string.top3)}  n/a")
        }
    }
}