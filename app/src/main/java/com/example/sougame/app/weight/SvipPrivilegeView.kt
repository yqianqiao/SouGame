package com.example.sougame.app.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.collection.arrayMapOf
import androidx.core.content.ContextCompat
import com.example.sougame.R
import com.example.sougame.app.ext.dp2px


/**
 *   Created by YX on 2020-07-24 17:49.
 */
class SvipPrivilegeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_title)
        strokeWidth = 0f
        textSize = 12f.dp2px()
        textAlign = Paint.Align.CENTER
    }

    private var titleWidth = 0f
    private var titleHeight = 50f.dp2px()
    private var tabHeight = 30f.dp2px()
    private var maxWidth = 0f
    private var maxHeight = 0f

    private var lastX = 0
    private var lastY = 0

    private var horizontalTitle =
        arrayOf("特权名称", "VIP1", "VIP2", "VIP3", "SVIP1", "SVIP2", "SVIP3", "SVIP4", "SVIP5")
    private var verticalTitle =
        arrayOf("积分倍率", "专属游戏礼包", "专属客服", "普通转盘", "高级转盘", "平台专属称号", "免费折扣券")

    private val tabMap = arrayMapOf(
        0 to arrayOf("特权名称", "VIP1", "VIP2", "VIP3", "SVIP1", "SVIP2", "SVIP3", "SVIP4", "SVIP5"),
        1 to arrayOf("积分倍率", "10", "10", "10", "10", "10", "10", "10", "10"),
        2 to arrayOf("专属游戏礼包", "无", "无", "无", "VIP礼包", "VIP礼包", "VIP礼包", "VIP礼包", "VIP礼包"),
        3 to arrayOf(
            "专属客服",
            "无",
            "无",
            "无",
            "SVIP专属客服",
            "SVIP专属客服",
            "SVIP专属客服",
            "SVIP专属客服",
            "SVIP专属客服"
        ),
        4 to arrayOf("普通转盘", "1", "1", "1", "0", "0", "0", "0", "0"),
        5 to arrayOf("高级转盘", "0", "0", "0", "1", "1", "1", "1", "1"),
        6 to arrayOf("平台专属称号", "士兵", "主教", "骑士", "战车", "祭司", "堡垒", "国王", "皇后"),
        7 to arrayOf("免费折扣券", "9.8折卷", "9.8折卷", "9.8折卷", "9.5折卷", "9.5折卷", "9折卷", "8.5折卷", "8折卷")
    )


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        titleWidth = width / 4f
        maxWidth = horizontalTitle.size * titleWidth + 8
        maxHeight = titleHeight + tabHeight * 7 + 9
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制title
        canvas.drawLine(0f, 0f, maxWidth, 0f, paint)
        canvas.drawLine(0f, titleHeight, maxWidth, titleHeight, paint)
        //绘制横线
        for (i in 1..7) {
            canvas.drawLine(
                0f,
                titleHeight + i * tabHeight,
                maxWidth,
                titleHeight + i * tabHeight,
                paint
            )
        }
        //绘制竖线
        for (i in 0 until 11) {
            canvas.drawLine(titleWidth * i, 0f, titleWidth * i, maxHeight, paint)
        }
        //绘制文字
        for ((k, v) in tabMap) {
            if (k == 0) {
                for (i in v.indices) {
                    canvas.drawText(v[i], titleWidth / 2 + titleWidth * i, titleHeight / 2, paint)
                }
            } else {
                for (i in v.indices) {
                    canvas.drawText(
                        v[i],
                        titleWidth / 2 + titleWidth * i,
                        titleHeight / 2 + tabHeight / 2 + tabHeight * k,
                        paint
                    )
                }
            }
        }
    }



//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val x = event.rawX.toInt()
//        val y = event.rawY.toInt()
//        when (event.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                // 记录触摸点坐标
//                lastX = x
//                lastY = y
//
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val offsetX = x - lastX
//                val offsetY = y - lastY
//                layout(getLeft() + offsetX,
//                    getTop() + offsetY,
//                    getRight() + offsetX,
//                    getBottom() + offsetY)
//                //重新设置初始化坐标
//                lastX = x
//                lastY = y
//            }
//        }
//        return true
//    }
}