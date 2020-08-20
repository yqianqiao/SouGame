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
 * Date: 2020/6/19 15:41
 * Description: com.example.sougame.ui.adapter
 */
class HandpickAdapter(data: ArrayList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_handpick, data) {
    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.getView<ImageView>(R.id.iv_img).loadFilletImage(item.handpick_img)
        holder.setText(R.id.tv_title, item.subject)
            .setText(R.id.tv_info, item.description);
    }
}