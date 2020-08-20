package com.example.sougame.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameListBean

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 16:49
 * Description: com.example.sougame.ui.adapter
 */
class RecommendAdapter(data: ArrayList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_recommend, data) {
    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.getView<ImageView>(R.id.iv_icon).loadFilletImage(item.icon)
        holder.setText(R.id.tv_title, item.subject)
    }
}