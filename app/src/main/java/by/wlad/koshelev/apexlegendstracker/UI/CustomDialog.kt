package by.wlad.koshelev.apexlegendstracker.UI


import android.app.AlertDialog
import android.content.Context

import by.wlad.koshelev.apexlegendstracker.R

object CustomDialog {

    fun alertDialog(context: Context, style: Int = R.style.AlertDialog) = AlertDialog.Builder(context, style)

}