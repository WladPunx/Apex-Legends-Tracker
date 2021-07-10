package by.wlad.koshelev.apexlegendstracker

import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

/**
 * КНОПКИ ОСНОВНОГО УПРАВЛЕНИЯ ФРАГМЕНТАМИ
 */
object NavigateButton {

    // контекст, который инициализируется и будет использоваться в других функциях
    private lateinit var app: AppCompatActivity

    // контроллер
    private lateinit var navContr: NavController

    /**
     * главный метод "слушателей" кнопок
     */
    fun create(_app: AppCompatActivity) {
        app = _app // иниц. основной котекст для Объекта

        // инз контроллера
        navContr = app.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
            .findNavController()

        buttonPressed(app.search_btn_MainActiv) // дэфолтное включение кнопки

        // кнопка поиска
        app.search_btn_MainActiv.setOnClickListener {
            buttonPressed(it as ImageButton)
            buttonNavigate(R.id.searchFragment)
        }

        // кнопка просмотра БД
        app.listst_btn_MainActiv.setOnClickListener {
            buttonPressed(it as ImageButton)
            buttonNavigate(R.id.listsFragment)
        }

        // кнопка "изменений"
        app.changes_btn_MainActiv.setOnClickListener {
            buttonPressed(it as ImageButton)
            buttonNavigate(R.id.changesFragment)
        }
    }

    /**
     * ДИЗАЙН нажатой кнопки
     */
    private fun buttonPressed(viewOn: ImageButton) {
        // выключаем все кнопки
        buttonOff(app.search_btn_MainActiv)
        buttonOff(app.listst_btn_MainActiv)
        buttonOff(app.changes_btn_MainActiv)

        // нажатую кнопку включаем
        viewOn.background = ContextCompat.getDrawable(app, R.drawable.main_button_style_selected)
        viewOn.setColorFilter(ContextCompat.getColor(app, R.color.item_button_select))
    }

    /**
     * дизайне выключенной кнопки
     */
    private fun buttonOff(viewOff: ImageButton) {
        viewOff.background = ContextCompat.getDrawable(app, R.drawable.main_button_style_default)
        viewOff.setColorFilter(ContextCompat.getColor(app, R.color.item_button_default))
    }

    /**
     * навигация кнопки
     */
    private fun buttonNavigate(viewId: Int) {
        if (navContr.currentDestination?.id != viewId) {
            navContr.popBackStack()
            navContr.navigate(viewId)
        }
    }


}