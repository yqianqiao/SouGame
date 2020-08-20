package com.example.sougame.app.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import androidx.fragment.app.Fragment
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 15:42
 * Description: com.example.sougame.app.ext
 */
fun Int.formatNum(): String {
    if (this < 10000) {
        return "${this}人关注"
    }
    val df = DecimalFormat("#.0")
    df.roundingMode = RoundingMode.DOWN
    return "${df.format(this / 10000)}万人关注"
}

/**
 * 29% ->29
 */
fun String.progress(): Int {
    return substring(0, length - 1).toInt()

}


fun Float.dp2px(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}

fun Fragment.copy(text: String) {
    //获取剪贴板管理器：
    val cm = this.activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // 创建普通字符型ClipData
    val mClipData = ClipData.newPlainText(null, text)
    // 将ClipData内容放到系统剪贴板里。
    cm.setPrimaryClip(mClipData)
}


val Bitmap.compressImage
    get() = run {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        var options = 90
        while (baos.toByteArray().size / 1024 > 32) { // 循环判断如果压缩后图片是否大于32kb,大于继续压缩
            baos.reset() // 重置baos即清空baos
            this.compress(Bitmap.CompressFormat.JPEG, options, baos)// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10// 每次都减少10
        }
        val i1 = baos.toByteArray().size / 1024
        val isBm = ByteArrayInputStream(baos.toByteArray())// 把压缩后的数据baos存放到ByteArrayInputStream中
        val bitmap = BitmapFactory.decodeStream(isBm, null, null)// 把ByteArrayInputStream数据生成图片
        val i = bitmap!!.byteCount / 1024

        baos.toByteArray()
    }

fun Fragment.getRandomCode(): String {
    val SB = StringBuilder()
    val s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val c = s.toCharArray()
    val random = Random()
    for (i in 0..6) {
        SB.append(c[random.nextInt(c.size)])
    }
    return SB.toString()
}
