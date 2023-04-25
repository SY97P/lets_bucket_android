package com.bucket.letsbucket.util

import android.util.Log

object LogUtil {
    private var TAG: String = "MYLogUtil > "

    private var DEVELOPE_MODE: Boolean = true

    fun d(detail: String, comment: String) {
        if (DEVELOPE_MODE)
            Log.d(TAG+detail, comment)
    }
}