package by.wlad.koshelev.apexlegendstracker.UI.ChangesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.Segment
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.UI.CoolerView
import by.wlad.koshelev.apexlegendstracker.UI.SetImgFromInet
import kotlinx.android.synthetic.main.fragment_changes.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ChangesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_changes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * слушатель статуса загрузки
         */
        VM.vm.loadFlag.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                loadStatus_txt_ChangeFrag.setText("${getString(R.string.load_error)}")
                loadStatus_txt_ChangeFrag.visibility = View.VISIBLE
            } else if (it) {
                loadStatus_txt_ChangeFrag.setText("${getString(R.string.load_status)}")
                loadStatus_txt_ChangeFrag.visibility = View.VISIBLE
            } else {
                loadStatus_txt_ChangeFrag.visibility = View.INVISIBLE
            }
        })


        /**
         * кнопка сохранения
         */
        saveGamer_btn_ChangeFrag.setOnClickListener {
            MainScope().launch {
                VM.vm.saveGamer()
            }
        }


        /**
         * колонка данных ИНЕТА
         */
        VM.vm.gmsInet.observe(viewLifecycleOwner, Observer {
            if (VM.vm.gmsInet.value != null) {
                setChangeColumn()

                layout_ChangeFrag.visibility = View.VISIBLE

                val lifeTimeStat: Segment = getLifeTimeStats(it)

                // установка аватара
                SetImgFromInet.set(it.data.platformInfo.avatarUrl, avatar_img_ChangeFrag)

                // ранг
                try {
                    SetImgFromInet.set(lifeTimeStat.stats.rankScore.metadata.iconUrl, newRank_img_ChangeFrag)
                } catch (e: Exception) {
                }


                // имя и цвет платформы
                CoolerView.setNameWithColor(activity as AppCompatActivity, gamerName_txt_ChangeFrag, it)


                // новые убийства
                try {
                    new_kills_txt_ChangeFrag.setText("${lifeTimeStat.stats.kills.displayValue}")
                } catch (ex: java.lang.Exception) {
                    new_kills_txt_ChangeFrag.setNA()
                }


                // новые победы
                try {
                    newWins_txt_ChangeFrag.setText("${lifeTimeStat.stats.winsWithFullSquad.displayValue}")
                } catch (ex: java.lang.Exception) {
                    newWins_txt_ChangeFrag.setNA()
                }

                // новые топ3
                try {
                    newTop_txt_ChangeFrag.setText("${lifeTimeStat.stats.timesPlacedtop3.displayValue}")
                } catch (ex: java.lang.Exception) {
                    newTop_txt_ChangeFrag.setNA()
                }


                // NULL NULL NULL NULL NULL NULL NULL NULL NULL NULL
            } else layout_ChangeFrag.visibility = View.GONE
        })


        /**
         * колонка данных ЛОКАЛЬНЫХ
         */
        VM.vm.gmsLocal.observe(viewLifecycleOwner, Observer {
            if (VM.vm.gmsLocal.value != null) {
                setChangeColumn()

                val lifeTimeStat: Segment = getLifeTimeStats(it)

                // дата сохраненный статистики
                date_txt_ChangeFrag.setText("${CoolerView.getCoolerDate(it, activity as AppCompatActivity)}")


                // ранг
                try {
                    SetImgFromInet.set(lifeTimeStat.stats.rankScore.metadata.iconUrl, oldRank_img_ChangeFrag)
                } catch (e: Exception) {
                }


                // старые убийства
                try {
                    old_kills_txt_ChangeFrag.setText("${lifeTimeStat.stats.kills.displayValue}")
                } catch (ex: java.lang.Exception) {
                    old_kills_txt_ChangeFrag.setNA()
                }

                // старые победы
                try {
                    oldWins_txt_ChangeFrag.setText("${lifeTimeStat.stats.winsWithFullSquad.displayValue}")
                } catch (ex: java.lang.Exception) {
                    oldWins_txt_ChangeFrag.setNA()
                }


                // старые  топ3
                try {
                    oldTop_txt_ChangeFrag.setText("${lifeTimeStat.stats.timesPlacedtop3.displayValue}")
                } catch (ex: java.lang.Exception) {
                    oldTop_txt_ChangeFrag.setNA()
                }


                // NULL NULL NULL NULL NULL NULL NULL NULL
            } else {
                // дата сохраненный статистики
                date_txt_ChangeFrag.setText(getString(R.string.noSave))

                // киллы
                old_kills_txt_ChangeFrag.setNA()

                // победы
                oldWins_txt_ChangeFrag.setNA()

                //топ 3
                oldTop_txt_ChangeFrag.setNA()
            }
        })

    }


    /**
     * колонка данных ИЗМЕНЕНИЙ
     */
    private fun setChangeColumn() {
        if (VM.vm.gmsInet.value != null && VM.vm.gmsLocal.value != null) {


            val newLifeTime: Segment = getLifeTimeStats(VM.vm.gmsInet.value!!)
            val oldLifeTime: Segment = getLifeTimeStats(VM.vm.gmsLocal.value!!)


            // убийства
            try {
                val a: Int = (newLifeTime.stats.kills.value - oldLifeTime.stats.kills.value).toInt()
                setDiffView(a, diff_kills_txt_ChangeFrag)
            } catch (ex: java.lang.Exception) {
                diff_kills_txt_ChangeFrag.setText("")
            }


            //  победы
            try {
                val a: Int = (newLifeTime.stats.winsWithFullSquad.value - oldLifeTime.stats.winsWithFullSquad.value).toInt()
                setDiffView(a, diffWins_txt_ChangeFrag)
            } catch (ex: java.lang.Exception) {
                diffWins_txt_ChangeFrag.setText("")
            }


            //  топ3
            try {
                val a: Int = (newLifeTime.stats.timesPlacedtop3.value - oldLifeTime.stats.timesPlacedtop3.value).toInt()
                setDiffView(a, diffTop_txt_ChangeFrag)
            } catch (ex: java.lang.Exception) {
                diffTop_txt_ChangeFrag.setText("")
            }


            // NULL NULL NULL NULL NULL NULL NULL NULL NULL
        } else {
            // киллы
            diff_kills_txt_ChangeFrag.setText("")
            // победы
            diffWins_txt_ChangeFrag.setText("")
            // топ3
            diffTop_txt_ChangeFrag.setText("")

        }


    }


    /**
     * метод сортировки статы
     */
    private fun getLifeTimeStats(it: GamerStats): Segment {
        val curentStats: GamerStats = it
        val _lifeTimeStat: MutableList<Segment> = curentStats.data.segments
            .asSequence()
            .filter { it.metadata.name == "Lifetime" }
            .toMutableList()
        val lifeTimeStat: Segment = _lifeTimeStat[0]
        return lifeTimeStat
    }


    /**
     * установка поля "разницы" статов
     */
    private fun setDiffView(a: Int, txtView: TextView) {
        txtView.setText("${a}")
        if (a > 0) txtView.setTextColor(ContextCompat.getColor(activity as AppCompatActivity, R.color.positiveChange))
        else if (a < 0) txtView.setTextColor(ContextCompat.getColor(activity as AppCompatActivity, R.color.negativeChange))
    }


    /**
     * N/A данных
     */
    private fun TextView.setNA() {
        this.setText("n/a")
    }


}