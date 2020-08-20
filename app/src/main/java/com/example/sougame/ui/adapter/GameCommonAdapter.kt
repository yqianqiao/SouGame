package com.example.sougame.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.app.ext.formatNum
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameListBean


/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/17 15:21
 * Description: com.example.sougame.ui.adapter
 */
class GameCommonAdapter :
    BaseQuickAdapter<GameListBean, BaseViewHolder>(R.layout.item_game_common) {
    override fun convert(holder: BaseViewHolder, item: GameListBean) {
        holder.getView<ImageView>(R.id.iv_icon).loadFilletImage(item.icon)

        holder.setText(R.id.tv_title, item.subject)
        val tagView = arrayOf(
            holder.getView<TextView>(R.id.tv_tag_1),
            holder.getView(R.id.tv_tag_2),
            holder.getView(R.id.tv_tag_3)
        )
        val tags: List<String> = item.tag.split(",")
        for (i in tags.indices) {
            val textView = tagView[i]
            if (!TextUtils.isEmpty(tags[i])) {
                textView.visibility = View.VISIBLE
                textView.text = tags[i]
            }
        }
        holder.setText(R.id.tv_info, item.description)
            .setText(R.id.tv_play_num, item.playnum.formatNum())
    }
}