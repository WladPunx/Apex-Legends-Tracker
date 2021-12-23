package by.wlad.koshelev.apexlegendstracker

import android.util.Log


fun DebugDo(foo: () -> Unit) {
    if (BuildConfig.DEBUG && BuildConfig.BUILD_TYPE != "tst") {
        foo()
    }
}

fun LogInfo(txt: String, tag: String = "!!!") {
    if (BuildConfig.DEBUG) {
        try {
            System.out.println("${tag}: ${txt}")
            Log.e(tag, txt)
        } catch (xxx: Exception) {
        }
    }
}


fun LogError(ex: Exception, tag: String = "!!!!ERROR") {
    if (BuildConfig.DEBUG) {
        ex.printStackTrace()
        val res = ex.stackTraceToString()
        LogInfo(res, tag)
    }
}

