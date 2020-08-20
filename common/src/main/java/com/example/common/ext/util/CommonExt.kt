package com.example.common.ext.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 18:52
 * Description: com.example.common.ext.util
 */

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}