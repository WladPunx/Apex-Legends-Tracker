package by.wlad.koshelev.apexlegendstracker.UI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.net.URL

object ImageConvertor {

    fun urlToBitmap(url: String): Bitmap {
        val newurl = URL(url)
        val mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
        return mIcon_val
    }


    fun bitmapToByte(mIcon_val: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        mIcon_val.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        return image
    }


    fun urlToByte(url: String): ByteArray {
        return bitmapToByte(urlToBitmap(url))
    }


    fun byteToBitmap(myByte: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(myByte, 0, myByte.size)
    }


}