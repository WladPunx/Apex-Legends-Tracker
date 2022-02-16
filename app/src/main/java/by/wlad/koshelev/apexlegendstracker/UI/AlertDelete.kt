package by.wlad.koshelev.apexlegendstracker.UI

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats.GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object AlertDelete {

    fun deleteOneGamer(gamer: GamerStats, app: AppCompatActivity) {
        CustomDialog.alertDialog(app)
            .setCancelable(false)
            .setTitle(app.getString(R.string.suuure))
            .setMessage("${app.getString(R.string.deleteOneGamer)} ${gamer.data.platformInfo.platformUserId} ?")

            .setPositiveButton(app.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                MainScope().launch {
                    VM.vm.deleteOneGamer(gamer)
                }

            })

            .setNegativeButton(app.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .create()
            .show()
    }


    fun dellAllGamers(app: AppCompatActivity) {
        CustomDialog.alertDialog(app)
            .setCancelable(false)
            .setTitle(app.getString(R.string.suuure))
            .setMessage(app.getString(R.string.deleteAllGamers))

            .setPositiveButton(app.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                MainScope().launch {
                    VM.vm.dellAllGamers()
                }
            })

            .setNegativeButton(app.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .create()
            .show()
    }
}