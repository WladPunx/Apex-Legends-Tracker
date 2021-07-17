package by.wlad.koshelev.apexlegendstracker

import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

object SetImgFromInet {

    fun set(url: String, view: ImageView) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newurl = URL(url)
                val mIcon_val =
                    BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
                withContext(Dispatchers.Main) {
                    view.setImageBitmap(mIcon_val)
                }
            } catch (e: Exception) {
            }
        }
    }


}