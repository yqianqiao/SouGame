package com.example.sougame.app.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.sougame.app.ext.dp2px


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/11 17:33
 * Description: com.example.sougame.app.weight
 */
class VipLevelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val bottomText = "VIP会员最高可达VIP3,SVIP会员最高可达SVIP5"
    private val vipLevel =
        arrayOf("VIP1", "VIP2", "VIP3", "SVIP1", "SVIP2", "SVIP3", "SVIP4", "SVIP5")
    private val vipNums =
        arrayOf("50", "200", "500", "1000", "3000", "10000", "20000", "500000")

    private val standardSize = 12f.dp2px()

    private val textRect = Rect()
    private val rectF = RectF()
    private var path = Path()
    private var startPoint = PointF()
    private var centerPoint = PointF()
    private var endPoint = PointF()

    private var currentLevel = 0


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize =14f.dp2px()
//        color = ContextCompat.getColor(context, R.color.color_ef)
        color = Color.RED

        strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.getTextBounds(bottomText, 0, bottomText.length, textRect)

        val startDp = (width - textRect.width() - standardSize * 2) / 2f

        rectF.set(
            startDp,
            height - textRect.height() - standardSize * 2,
            width - startDp,
            height - standardSize
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //底部绘制文字
        paint.strokeWidth = textRect.height() + 12f.dp2px()
        canvas.drawLine(
            (width - (textRect.width())) / 2f,
            height - standardSize * 1.5f - textRect.height() / 2,
            width - (width - (textRect.width())) / 2f,
            height - standardSize * 1.5f - textRect.height() / 2,
            paint
        )

        paint.strokeWidth = 8f.dp2px()
        paint.color = Color.WHITE

        val fontMetrics = paint.fontMetricsInt
        val baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(bottomText, rectF.centerX(), baseline, paint)



        paint.textSize = standardSize
        val space = width / 8
        var count = 0
        var progress = standardSize
        val levelName = height - rectF.height() - standardSize * 2
        for (i in 0 until 8) {
            val vipName = vipLevel[i]
            //绘制文字
            paint.color = Color.BLACK
            paint.getTextBounds(vipName, 0, vipName.length, textRect)
            canvas.drawText(vipName, count + space / 2f, levelName, paint)

//            //绘制柱形
            paint.color = Color.RED
            val hei = levelName - standardSize * 2
            canvas.drawLine(
                count + space / 2f,
                hei,
                count + space / 2f,
                hei - progress * 1.3f,
                paint
            )

            //柱形上文字
            canvas.drawText(
                vipNums[i],
                count + space / 2f,
                hei - progress * 1.3f - standardSize * 2.5f,
                paint
            )
            when (i) {
                0 -> startPoint.set(
                    count + space / 2f,
                    hei - progress * 1.3f - standardSize * 5f
                )
                vipLevel.size / 2 -> centerPoint.set(
                    count + space / 2f,
                    hei - progress * 1.3f - standardSize * 2f
                )
                vipLevel.size - 1 -> endPoint.set(
                    count + space / 2f,
                    hei - progress * 1.3f - standardSize * 7f
                )
            }

            if (i == currentLevel) {
                canvas.drawCircle(
                    count + space / 2f,
                    hei - progress * 1.3f - standardSize * 10f,
                    8f,
                    paint
                )

            }

            progress += standardSize
            count += space
        }

        paint.strokeWidth = 0f
        paint.style = Paint.Style.STROKE
        //绘制曲线
        path.moveTo(startPoint.x, startPoint.y)
        path.quadTo(centerPoint.x, centerPoint.y, endPoint.x, endPoint.y)
        canvas.drawPath(path, paint)


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        // 获取宽-测量规则的模式和大小
        val widthMode = MeasureSpec.getMode(widthMeasureSpec);
        val widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        val heightMode = MeasureSpec.getMode(heightMeasureSpec);
        val heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        val mWidth = 400;
        val mHeight = 400;

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }
}

