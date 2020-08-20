package com.example.sougame.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sougame.app.ext.loadImage
import com.example.sougame.data.model.bean.Mobileswipe
import com.example.sougame.data.model.bean.Mobileswipe1
import com.youth.banner.adapter.BannerAdapter


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 17:09
 * Description: com.example.sougame.ui.adapter
 */
class BannerImageAdapter(var list: ArrayList<Mobileswipe>) :
    BannerAdapter<Mobileswipe, BannerImageAdapter.BannerViewHolder>(list) {

    inner class BannerViewHolder(view: ImageView) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view

    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: Mobileswipe,
        position: Int,
        size: Int
    ) {
        holder.imageView.loadImage(data.images)
    }
}