package by.wlad.koshelev.apexlegendstracker.UI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import by.wlad.koshelev.apexlegendstracker.LogError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URL

object ImageConvertor {
    fun urlToBitmap(url: String): Bitmap? {
        try {
            val newurl = URL(url)
            val mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
            return mIcon_val
        } catch (ex: Exception) {
            LogError(ex)
            return null
        }
    }


    fun bitmapToByte(mIcon_val: Bitmap?): ByteArray? {
        try {
            val stream = ByteArrayOutputStream()
            mIcon_val?.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            return image
        } catch (ex: Exception) {
            LogError(ex)
            return null
        }
    }


    fun urlToByte(url: String): ByteArray? {
        return bitmapToByte(urlToBitmap(url))
    }


    fun byteToBitmap(myByte: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(myByte, 0, myByte?.size ?: 0)
    }

    /**
     *
     *
     *
     *
     */


    fun setImgFromUrl(url: String, view: ImageView) {
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