package com.example.sougame.data.bindadapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.sougame.app.ext.addHttpString

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 11:48
 * Description: com.example.sougame.data.bindadapter
 */
object CustomBindAdapter {
    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url.addHttpString())
//            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view: ImageView, url: String) {
//        Glide.with(view.context.applicationContext)
//            .load(url.addHttpString())
//            .apply(RequestOptions.bitmapTransform(CircleCrop()))
//            .transition(DrawableTransitionOptions.withCrossFade(500))
//            .into(view)
    }

    @BindingAdapter("filletImageUrl")
    @JvmStatic
    fun filletImageUrl(view: ImageView, url: String) {
        Log.e("TAG", ": $url" );
        Glide.with(view.context.applicationContext)
            .load(url.addHttpString())
            .into(view)
    }
}