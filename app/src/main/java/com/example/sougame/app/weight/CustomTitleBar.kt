package com.example.sougame.app.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.common.ext.nav
import com.example.sougame.R
import kotlinx.android.synthetic.main.layout_title.view.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/7/2 16:43
 * Description: com.example.sougame.app.weight
 */
class CustomTitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_title, this)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        val title = a.getString(R.styleable.CustomTitleBar_title)
        a.recycle()

        view.findViewById<TextView>(R.id.tv_title_name).text = title ?: ""

        view.findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            nav(this).navigateUp()
        }
    }

    fun back(action: () -> Unit) {
        iv_back.setOnClickListener {
            action()
        }
    }

}