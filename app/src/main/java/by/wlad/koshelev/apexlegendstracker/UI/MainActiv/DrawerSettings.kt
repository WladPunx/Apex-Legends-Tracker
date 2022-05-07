package by.wlad.koshelev.apexlegendstracker.UI.MainActiv

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.sharedpref.SharedPref
import kotlinx.android.synthetic.main.activity_main.*

object DrawerSettings {

    2222


    /**
     * настройки Дровера
     */
    fun set(app: MainActivity) {
        val navContr = app.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchFragment,
                R.id.listsFragment,
                R.id.changesFragment
            ), app.myDrawer
        )

        app.drawerToolBar_MainActiv.setupWithNavController(navContr, appBarConfiguration)
        app.navView.setupWithNavController(navContr)

        app.navView.setNavigationItemSelectedListener {
            if (navContr.currentDestination?.id != it.itemId) {
                navContr.popBackStack()
                navContr.navigate(it.itemId)
                app.myDrawer.close()
            }
            return@setNavigationItemSelectedListener true
        }


        // проверка стиля
        checkStyle(app)

    }


    /**
     * проверка стиля
     */
    fun checkStyle(app: MainActivity) {
        if (SharedPref(app.application).style) {
            app.drawerToolBar_MainActiv.visibility = View.VISIBLE
            app.navigateLayout_MainActiv.visibility = View.GONE
            app.HEADER_drawer_MainActiv.visibility = View.VISIBLE
        } else {
            app.drawerToolBar_MainActiv.visibility = View.GONE
            app.navigateLayout_MainActiv.visibility = View.VISIBLE
            app.HEADER_drawer_MainActiv.visibility = View.GONE
        }
    }

    /**
     * установка стиля
     */
    fun setStyle(cont: AppCompatActivity) {
        val sharedPref = SharedPref(cont.application)
        sharedPref.style = !sharedPref.style
        if (cont is MainActivity) checkStyle(cont)
    }
}