package by.wlad.koshelev.apexlegendstracker

import android.util.Log


fun LogInfo(txt: String) {
    if (BuildConfig.DEBUG) {
        try {
            Log.e("!!!!", txt)
        } catch (ex: Exception) {
            System.out.println("!!!!: ${txt}")
        }
    }
}


fun LogError(ex: Exception) {
    if (BuildConfig.DEBUG) {
        try {
            Log.e("ERROR", "${ex.stackTraceToString()}")
        } catch (e: Exception) {
            System.out.println("ERROR: ${ex.stackTraceToString()}")
        }
    }
}


fun DebugDo(foo: () -> Unit) {
    if (BuildConfig.DEBUG) {
        foo()
    }
}