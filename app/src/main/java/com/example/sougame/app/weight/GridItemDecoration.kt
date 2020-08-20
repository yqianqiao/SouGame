package com.example.sougame.app.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/3 16:41
 * Description: com.example.sougame.app.weight
 */
class GridItemDecoration(context: Context, height: Int? = null, color: Int? = null) :
    RecyclerView.ItemDecoration() {
    private var ATTRS = intArrayOf(android.R.attr.listDivider)
    private var divider: Drawable

    init {
        if (height == null) {
            val a = context.obtainStyledAttributes(ATTRS)
            divider = a.getDrawable(0)!!
            a.recycle()
        } else {
            val shapeDrawable = GradientDrawable()
            shapeDrawable.setColor(color!!)
            shapeDrawable.shape = GradientDrawable.RECTANGLE
            shapeDrawable.setSize(height, height)
            divider = shapeDrawable

        }


    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state)
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount //获取可见item的数量
        val spanCount = getSpanCount(parent)
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin + divider.intrinsicWidth
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
            if (i < spanCount) { //画第一行顶部的分割线
                drawHorizontalForFirstRow(c, child)
            }
        }
    }

    private fun drawHorizontalForFirstRow(c: Canvas, child: View) {
        val params = child.layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin - divider.intrinsicWidth
        val top = child.top - params.topMargin - divider.intrinsicHeight
        val right = child.right + params.rightMargin + divider.intrinsicWidth
        val bottom = top + divider.intrinsicHeight
        divider.setBounds(left, top, right, bottom)
        divider.draw(c)
    }

    private fun drawVerticalForFirstColum(c: Canvas, child: View) {
        val params = child.layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin - divider.intrinsicWidth
        val top = child.top - params.topMargin
        val right = child.left - params.leftMargin
        val bottom = top + child.height + divider.intrinsicHeight
        divider.setBounds(left, top, right, bottom)
        divider.draw(c)
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + divider.intrinsicWidth
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
            if (isFirstColum(parent, i, getSpanCount(parent))) { //画第一列左边分割线
                drawVerticalForFirstColum(c, child)
            }
        }
    }

    private fun isLastColum(
        parent: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true
            }
        }
        return false
    }


    //是否为第一列
    private fun isFirstColum(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
        val layoutManager = parent.getLayoutManager()
        if (layoutManager is GridLayoutManager) { //网格布局
            if ((pos + 1) % spanCount == 1) {
                return true
            }
        }
        return false
    }

    //是否为第一行
    private fun isFirstRaw(pos: Int, spanCount: Int): Boolean {
        if (pos < spanCount) {
            return true
        }
        return false
    }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        val spanCount = getSpanCount(parent) //列数

        when {
            itemPosition == 0 -> { //第一行第一个，四边都画
                outRect.set(
                    divider.intrinsicWidth, divider.intrinsicHeight,
                    divider.intrinsicWidth, divider.intrinsicHeight
                )
            }
            isFirstRaw(itemPosition, spanCount) -> { //第一行，画上下右三边
                outRect.set(
                    0,
                    divider.intrinsicHeight,
                    divider.intrinsicWidth,
                    divider.intrinsicHeight
                )
            }
            isFirstColum(parent, itemPosition, spanCount) -> { //第一列，画左右下三边
                outRect.set(
                    divider.intrinsicWidth,
                    0,
                    divider.intrinsicWidth,
                    divider.intrinsicHeight
                )
            }
            else -> { //其他，画右下两边
                outRect.set(0, 0, divider.intrinsicWidth, divider.intrinsicHeight)
            }
        }
    }


}