package com.example.sougame.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sougame.R
import com.example.sougame.app.ext.formatNum
import com.example.sougame.app.ext.initImage
import com.example.sougame.app.ext.loadFilletImage
import com.example.sougame.data.model.bean.GameListBean
import com.example.sougame.databinding.ItemGameDetailsBinding
import kotlinx.android.synthetic.main.item_game_common.*

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/19 18:35
 * Description: com.example.sougame.ui.adapter
 */
class NewGameAdapter(data: ArrayList<GameListBean>) :
    BaseQuickAdapter<GameListBean, BaseDataBindingHolder<ItemGameDetailsBinding>>(
        R.layout.item_game_details,
        data
    ) {


    override fun convert(
        holder: BaseDataBindingHolder<ItemGameDetailsBinding>,
        item: GameListBean
    ) {
        holder.dataBinding?.run {
            gameInfo = item
            itemGameCommon.ivIcon.loadFilletImage(item.icon)
            val tabs = item.tag.split(",")
            val tagView =
                arrayOf(itemGameCommon.tvTag1, itemGameCommon.tvTag2, itemGameCommon.tvTag3)
            for (i in tabs.indices) {
                val textView: TextView = tagView[i]
                if (!TextUtils.isEmpty(tabs[i])) {
                    textView.visibility = View.VISIBLE
                    textView.text = tabs[i]
                }
            }
            itemGameCommon.tvPlayNum.text = item.playnum.formatNum()
        }
        holder.dataBinding?.rvGameImage?.initImage(item.images_app.split(",").toMutableList())
    }
}