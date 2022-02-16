package by.wlad.koshelev.apexlegendstracker.utils

import android.util.Log
import by.wlad.koshelev.apexlegendstracker.BuildConfig


inline fun debugDo(foo: () -> Unit) {
    if (BuildConfig.DEBUG && BuildConfig.BUILD_TYPE != "tst") {
        foo()
    }
}

private fun logCreator(txt: String, part: Int) {
    if (BuildConfig.DEBUG) {
        val tag = "!!!"
        val cti = Throwable().stackTrace[part]
        val str = "${cti.methodName} / (${cti.fileName}:${cti.lineNumber}) : ${txt}\n."
        try {
            Log.e(tag, str)
        } catch (xxx: Exception) {
            System.out.println("${tag}: ${str}")
        }
    }
}


fun logInfo(txt: String) {
    logCreator(txt, 2)
}

fun Throwable.logError() {
    logCreator(this.stackTraceToString(), 2)
}


